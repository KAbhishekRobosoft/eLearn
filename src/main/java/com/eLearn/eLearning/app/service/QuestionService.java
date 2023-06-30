package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.Question;
import com.eLearn.eLearning.app.response.OpResponse;

public interface QuestionService {
    public OpResponse create(Question question);

    public OpResponse getByQuizId(Integer quizId);

    public OpResponse getAllQuestion();

    public OpResponse update(Question question);

    public OpResponse deleteById(Integer questionId);
}
