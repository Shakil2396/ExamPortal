package com.substring.quiz.service;

import com.substring.quiz.dtos.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@FeignClient(name = "CATEGORY-SERVICE")
public interface CategoryFeignService {

    // get all the categories
    @GetMapping("/api/v1/categories")
    List<CategoryDto> findAll();

    // get single category
    @GetMapping("/api/v1/categories/{id}")
    CategoryDto findById(@PathVariable String id);


    //create new category
    @PostMapping("/api/v1/categories")
    CategoryDto create(@RequestBody CategoryDto categoryDto);

    //update category
    @PutMapping("/api/v1/categories/{id}")
    CategoryDto update(@PathVariable String id, @RequestBody CategoryDto categoryDto);

    //delete category
    @DeleteMapping("/api/v1/categories/{id}")
    void delete(@PathVariable String id);
}
