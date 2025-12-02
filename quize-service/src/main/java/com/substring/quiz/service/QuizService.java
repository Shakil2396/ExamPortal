package com.substring.quiz.service;

import com.substring.quiz.dtos.QuizDto;

import java.util.List;


public interface QuizService {

    QuizDto create(QuizDto quizDto);

    QuizDto update(String quizId, QuizDto quizDto);

    void delete(String quizId);

    List<QuizDto> findAll();

    QuizDto findById(String quizId);

    List<QuizDto> findByCategory(String categoryId);

}
