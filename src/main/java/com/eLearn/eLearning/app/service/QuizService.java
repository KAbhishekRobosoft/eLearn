package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.Quiz;
import com.eLearn.eLearning.app.request.AnswerRequest;
import com.eLearn.eLearning.app.response.OpResponse;

public interface QuizService {
    public OpResponse create(Quiz quiz);

    public OpResponse getBySectionId(Integer sectionId);

    public OpResponse getAllQuiz();

    public OpResponse update(Quiz quiz);

    public OpResponse deleteById(Integer quizId);

    public OpResponse checkAnswers(AnswerRequest request);
}
