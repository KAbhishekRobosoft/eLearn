package com.eLearn.eLearning.app.service;

import com.eLearn.eLearning.app.entity.Completion;
import com.eLearn.eLearning.app.request.CompletionRequest;
import com.eLearn.eLearning.app.response.OpResponse;

public interface CompletionService {
    public OpResponse markComplete(Completion completion);

    public OpResponse getByUserAndLessonId(CompletionRequest request);

}
