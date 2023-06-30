package com.eLearn.eLearning.app.service;

import com.eLearn.eLearning.app.entity.Question;
import com.eLearn.eLearning.app.repository.QuestionRepository;
import com.eLearn.eLearning.app.response.OpResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository questionRepo;
    @Override
    public OpResponse create(Question question) {
        Optional<Question> questionData= questionRepo.findByQuestion(question.getQuestion());

        if(questionData.isEmpty()){
            question.setCreatedDate(LocalDateTime.now().toString());
            questionRepo.save(question);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(question)
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Question already created")
                .build();
    }

    @Override
    public OpResponse getByQuizId(Integer quizId) {
        List<Question> questionData= questionRepo.findByQuizid(quizId);

        if(questionData.size() > 0){
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(questionData)
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No questions available")
                .build();
    }

    @Override
    public OpResponse getAllQuestion() {
        List<Question> questionData= questionRepo.findAll();
        if(questionData.isEmpty()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("No question available")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(questionData)
                .build();
    }

    @Override
    public OpResponse update(Question question) {
        question.setCreatedDate(LocalDateTime.now().toString());
        questionRepo.save(question);
        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Updated successfully")
                .build();
    }

    @Override
    public OpResponse deleteById(Integer questionId) {
        if(questionRepo.findById(questionId).isPresent()){
            questionRepo.deleteById(questionId);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Id with"+" "+questionId+" "+"deleted successfully")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No question available")
                .build();
    }
}
