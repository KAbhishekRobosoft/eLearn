package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.Lesson;
import com.eLearn.eLearning.app.entity.Likes;
import com.eLearn.eLearning.app.entity.SubTopic;
import com.eLearn.eLearning.app.repository.LessonRepository;
import com.eLearn.eLearning.app.repository.LikeRepository;
import com.eLearn.eLearning.app.repository.SubTopicRepository;
import com.eLearn.eLearning.app.request.LikeRequest;
import com.eLearn.eLearning.app.response.LikeResponse;
import com.eLearn.eLearning.app.response.OpResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LikeServiceImpl implements LikeService {

    @Autowired
    LikeRepository likeRepo;

    @Autowired
    SubTopicRepository subTopicRepo;

    @Autowired
    LessonRepository lessonRepo;

    @Override
    public OpResponse likeOperation(LikeRequest request) {
        Optional<Likes> likeData= likeRepo.findByUseridAndSubtopicid(request.getUserid(), request.getSubtopicid());
        if(likeData.isPresent()){
            likeRepo.deleteById(likeData.get().getId());
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Subtopic removed from liked list")
                    .build();
        }
        Likes likes= new Likes();
        likes.setUserid(request.getUserid());
        likes.setSubtopicid(request.getSubtopicid());
        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(likeRepo.save(likes))
                .build();
    }

    @Override
    public OpResponse getByUserAndSubtopicid(LikeRequest request) {
        Optional<Likes> likeData= likeRepo.findByUseridAndSubtopicid(request.getUserid(), request.getSubtopicid());
        if(likeData.isPresent()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(true)
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(false)
                .build();
    }

    @Override
    public OpResponse getByUserId(int userid) {
        List<Likes> likeData= likeRepo.findByUserid(userid);
        if(likeData.isEmpty()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("No topics liked at")
                    .build();
        }

        List<LikeResponse> likeResponses= new ArrayList<>();
        likeData.forEach(ele ->{
            LikeResponse likeResponse= new LikeResponse();
            Optional<SubTopic> subTopic= subTopicRepo.findById(ele.getSubtopicid());
            Optional<Lesson> lessonData= lessonRepo.findById(subTopic.get().getLessonid());
            likeResponse.setSubtopicid(ele.getSubtopicid());
            likeResponse.setLessonName(lessonData.get().getName());
            likeResponse.setSubTopicName(subTopic.get().getName());
            likeResponses.add(likeResponse);
        });
        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(likeResponses)
                .build();
    }
}
