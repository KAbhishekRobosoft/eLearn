package com.eLearn.eLearning.app.service;

import com.eLearn.eLearning.app.entity.Likes;
import com.eLearn.eLearning.app.request.LikeRequest;
import com.eLearn.eLearning.app.response.LikeResponse;
import com.eLearn.eLearning.app.response.OpResponse;

import java.util.List;

public interface LikeService {
    public OpResponse likeOperation(LikeRequest request);

    public OpResponse getByUserAndSubtopicid(LikeRequest request);

    public OpResponse getByUserId(int userid);
}
