package com.substring.quiz.Services;

import com.substring.quiz.dtos.QuestionDto;
import com.substring.quiz.fuctions.QuizDto;

import java.util.List;

public interface QuestionGenerator {

    void generateAndSaveQuestions(QuizDto quizDto);

    List<QuestionDto> generateQuestions(String quizName, int numberOfQuestions, String description);

}
