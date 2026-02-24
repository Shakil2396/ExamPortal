package com.substring.quiz.fuctions;

import com.substring.quiz.QuestionGeneratorApp;
import com.substring.quiz.Services.QuestionGenerator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Slf4j
@Configuration
public class QuizService {

    @Autowired
    private QuestionGenerator questionGenerator;

    private Logger logger= LoggerFactory.getLogger(QuizService.class.getName());

    @Bean(name = "getQuizBinding")
    public Function<QuizDto,String> getQuizBinding() { //String ki jagah dto bhi bana kr k return kr sakte hai
        return quizDto -> {
            logger.info("Quiz created event received:");
            System.out.println("Quiz created event received:");
            System.out.println(quizDto.getTitle());
            System.out.println(quizDto.getId());
            this.questionGenerator.generateAndSaveQuestions(quizDto);
            return "Quiz created successfully";

        };
    }

//    @Bean(name = "getQuizBinding")
//    public Consumer<QuizDto> getQuizBinding() { //binding name same in yml file getQuizBinding
//        return quizDto -> {
//            System.out.println("Quiz created event received:");
//            System.out.println(quizDto.getTitle());
//            System.out.println(quizDto.getId());
//        };
//    }

}
