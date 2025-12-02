package com.substring.quiz.service.impl;

import com.substring.quiz.collections.Quiz;
import com.substring.quiz.dtos.CategoryDto;
import com.substring.quiz.dtos.QuizDto;
import com.substring.quiz.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    private final RestTemplate restTemplate;

    private final WebClient webClient;

//    private final WebClient.Builder webClientBuilder;

    private ModelMapper modelMapper;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(CategoryServiceImpl.class);

//    public CategoryServiceWebclientImpl(RestTemplate restTemplate, WebClient.Builder webClientBuilder, ModelMapper modelMapper) {
//        this.restTemplate = restTemplate;
//        this.webClientBuilder = webClientBuilder;
//        this.modelMapper = modelMapper;
//        this.webClient = webClientBuilder.baseUrl("lb://CATEGORY-SERVICE").build();
//    }


    public CategoryServiceImpl(RestTemplate restTemplate, WebClient webClient, ModelMapper modelMapper) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto findById(String categoryId) {

            try {
                CategoryDto category = this.webClient
                        .get()
                        .uri("/api/v1/categories/{id}", categoryId)  //first it will take base url then this url to call
                        .retrieve()                        //recieve data here
                        .bodyToMono(CategoryDto.class)     //mono means single ...retrieve data in single object
                        .block();                          //then i want blocking request so block

                return category;

            }catch (WebClientResponseException ex){

                if(ex.getStatusCode().equals(HttpStatus.NOT_FOUND)){
                    logger.error("category not found");
                } else if (ex.getStatusCode().equals(HttpStatus.INTERNAL_SERVER_ERROR)) {
                    logger.info("internal server error");
                }
                ex.printStackTrace();
            }
            return null;
    }


    //how to perform all the operation using webclient just understand the concept
    @Override
    public List<CategoryDto> findAll() {
        return this.webClient
                .get()
                .uri("/api/v1/categories")
                .retrieve()
                .bodyToFlux(CategoryDto.class) //flux for multiple object
                .collectList()
                .block();
    }

    //if by chance quiz ki jarurat pad jaye category create karne k liye to aise create krte hai from quiz service
    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        return this.webClient
                .post()
                .uri("/api/v1/categories")
                .bodyValue(categoryDto)
                .retrieve()
                .bodyToMono(CategoryDto.class)
                .block();

    }

    @Override
    public CategoryDto update(String categoryId, CategoryDto categoryDto) {
        return this.webClient
                .put()
                .uri("/api/v1/categories/{id}", categoryId)
                .bodyValue(categoryDto)
                .retrieve()
                .bodyToMono(CategoryDto.class)
                .block();
    }

    @Override
    public void delete(String categoryId) {
        this.webClient.delete()
                .uri("/api/v1/categories/{id}", categoryId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}
