package com.eLearn.eLearning.app.service;

import com.eLearn.eLearning.app.entity.Answer;
import com.eLearn.eLearning.app.repository.AnswerRepository;
import com.eLearn.eLearning.app.response.OpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerRepository answerRepo;

    @Override
    public OpResponse create(Answer answer) {
        Optional<Answer> answerData= answerRepo.findByQuestionid(answer.getQuestionid());
        if(answerData.isPresent()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Answer key already entered for following question")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(answerRepo.save(answer))
                .build();
    }

    @Override
    public OpResponse updateByQuestionId(Answer answerObj) {
        Optional<Answer> answer= answerRepo.findByQuestionid(answerObj.getQuestionid());

        if(answer.isPresent()){
           Answer data=  answer.get();
           data.setText(answerObj.getText());
           return OpResponse.builder()
                           .statusCode(HttpStatus.OK.value())
                           .message(answerRepo.save(data))
                           .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Failed to update")
                .build();
    }
}
