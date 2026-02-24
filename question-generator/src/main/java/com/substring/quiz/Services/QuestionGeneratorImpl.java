package com.substring.quiz.Services;

import com.substring.quiz.Collections.Question;
import com.substring.quiz.dtos.QuestionDto;
import com.substring.quiz.fuctions.QuizDto;
import com.substring.quiz.repositories.QuestionRepo;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuestionGeneratorImpl implements QuestionGenerator {


    private Logger logger = LoggerFactory.getLogger(QuestionGeneratorImpl.class);

    private QuestionRepo questionRepository;

    private ChatClient chatClient;  //in chat client internally use ChatModel to call open ai
    //it will provide abstaction layer between chat models

    private ModelMapper modelMapper;

        // sirf ChatClient name se bean nahi he spring ai pass

    public QuestionGeneratorImpl(ChatClient.Builder builder, QuestionRepo questionRepository, ModelMapper modelMapper) {//spring ai hame ChatClient.Builder yahi bean available karayega
        this.chatClient = builder.build();
        this.questionRepository = questionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void generateAndSaveQuestions(QuizDto quizDto) {
        List<QuestionDto> questionDtos = this.generateQuestions(quizDto.getTitle(), 10, quizDto.getDescription());
        //ye hame sare questions generate karke dega

        //en questions k ander hm quize ki id set karege
        List<Question> questionDtoList = questionDtos.stream().map(questionDto -> {
            questionDto.setQuizId(quizDto.getId());
            return this.modelMapper.map(questionDto, Question.class);
        }).toList();

        questionRepository.saveAll(questionDtoList);
        this.logger.info("Questions saved successfully");
        questionDtoList.forEach(e -> logger.info(e.getQuestion()));
    }

    @Override
    public List<QuestionDto> generateQuestions(String quizName, int numberOfQuestions, String description) {

        //we give some order to the model like following
                String systemString = """
                        As an Coding,Technology,Programing and Frameworks expert, your primary role is to generate high-quality questionDtos for quizzes.
                        """;

        //based on this prompt string it will generate
                String promptString = """
                Generate {numberOfQuestions} questionDtos for {quizName} quiz.
                Having description: {description}
                """;

                Map<String, Object> valuesForPrompt = Map.of(
                "numberOfQuestions", numberOfQuestions,
                "quizName", quizName,
                "description", description
        );


        return this.chatClient
                .prompt()
                .system(systemString)
                .user(userSpec -> userSpec.text(promptString).params(valuesForPrompt))
                .call() //yahase call karege
                .entity(new ParameterizedTypeReference<List<QuestionDto>>() {
                });

    }
}

