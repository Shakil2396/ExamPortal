package com.substring.quiz.category.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/api/v1/config")
public class ConfigTestController {

    @Value("${config.value}")  //agar ye value aa gayi matlb ye read kr pa raha he
    String configValue;

    @RequestMapping
    public String test() {
        return configValue;
    }

}

