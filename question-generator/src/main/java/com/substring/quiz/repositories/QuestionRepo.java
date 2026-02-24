package com.substring.quiz.repositories;

import com.substring.quiz.Collections.Question;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface QuestionRepo extends MongoRepository<Question,String> {

}
