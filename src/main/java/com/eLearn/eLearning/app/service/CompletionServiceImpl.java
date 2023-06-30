package com.eLearn.eLearning.app.service;

import com.eLearn.eLearning.app.entity.Completion;
import com.eLearn.eLearning.app.entity.SubTopic;
import com.eLearn.eLearning.app.repository.CompletionRepository;
import com.eLearn.eLearning.app.repository.SubTopicRepository;
import com.eLearn.eLearning.app.request.CompletionRequest;
import com.eLearn.eLearning.app.response.CompletionResponse;
import com.eLearn.eLearning.app.response.OpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompletionServiceImpl implements CompletionService {

    @Autowired
    CompletionRepository completionRepo;

    @Autowired
    SubTopicRepository subTopicRepo;


    @Override
    public OpResponse markComplete(Completion completion) {
        Optional<Completion> completionData= completionRepo.findByUseridAndSubtopicid(completion.getUserid(), completion.getSubtopicid());
        if(completionData.isPresent()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("User already completed this topic")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(completionRepo.save(completion))
                .build();
    }


    @Override
    public OpResponse getByUserAndLessonId(CompletionRequest request) {
        List<Completion> completionData= completionRepo.findByUseridAndLessonid(request.getUserid(), request.getLessonid());
        if(completionData.isEmpty()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("User hasn't completed any subtopics in this lesson yet")
                    .build();
        }
        List<SubTopic> subTopics= subTopicRepo.findByLessonid(request.getLessonid());
        CompletionResponse completionResponse= new CompletionResponse();
        completionResponse.setCompletion(completionData);
        completionResponse.setPercentCompletion((completionData.size() * 100) / subTopics.size());
        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(completionResponse)
                .build();
    }

}
