package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.Answer;
import com.eLearn.eLearning.app.response.OpResponse;

public interface AnswerService {

    public OpResponse create(Answer answer);
    public OpResponse updateByQuestionId(Answer answerObj);
}
