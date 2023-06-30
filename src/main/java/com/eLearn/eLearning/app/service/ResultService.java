package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.response.OpResponse;

public interface ResultService {

    public OpResponse getByUserId(int userid);

    public OpResponse deleteById(int resultid);

}
