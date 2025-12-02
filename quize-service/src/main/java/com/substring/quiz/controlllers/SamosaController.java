package com.substring.quiz.controlllers;

import com.substring.quiz.dtos.CategoryDto;
import com.substring.quiz.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/samosa")
public class SamosaController {


    private final CategoryService categoryService;

    public SamosaController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto categoryDto) {
        return this.categoryService.create(categoryDto);
    }

}
