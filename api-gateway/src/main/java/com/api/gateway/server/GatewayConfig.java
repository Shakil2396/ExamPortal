package com.api.gateway.server;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("category-service", route -> route.path("/CATEGORY-SERVICE/**")
                        .filters(f ->
                                f.rewritePath("/CATEGORY-SERVICE/?(?<remaining>.*)", "/${remaining}")
                                        .circuitBreaker(c -> c.setName("categoryCB").setFallbackUri("forward:/categoryFallback"))
                                        .requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()).setKeyResolver(userKeyResolver()) //pass 2 argument in that
                                        )

                        )
                        .uri("lb://CATEGORY-SERVICE")
                )
                .route("quiz-service", route -> route.path("/QUIZ-SERVICE/**")
                        .filters(f -> f.rewritePath("/QUIZE-SERVICE/?(?<remaining>.*)", "/${remaining}")
                                .retry(retry ->
                                        retry.setMethods(HttpMethod.GET) //we have to apply retries only get operations...
                                                .setRetries(3)
                                                .setBackoff(Duration.ofMillis(50), Duration.ofMillis(600), 2, true)) //in parameter control + p to check sequence of parameter what i have to pass
                                //50 mili second bad dobara try krna hai 1 bar fail aaye to...600 second maximum 600 tak sare retries ho jane chahiye..2 factor means pehla 50 m s bad retry hua tha to dusra 50*2=100 ms bad
                                // hoga 3 ra 200 ms but for this we have to true this based on previous values
                                // if we do false then its multiply with initial value like 50 100 150 like this

                                .requestRateLimiter(c -> c.setRateLimiter(redisRateLimiter()).setKeyResolver(userKeyResolver()))
                        )

                        .uri("lb://QUIZ-SERVICE"))
                .build();
    }


    @Bean
    public KeyResolver userKeyResolver() {

        return exchange -> {
            System.out.println(exchange.getRequest().getHeaders().getHost().getHostName());
            return Mono.just(exchange.getRequest().getHeaders().getHost().getHostName());
        };
    }


    @Bean
    public RedisRateLimiter redisRateLimiter() {
        return new RedisRateLimiter(5, 5);
    }
}

