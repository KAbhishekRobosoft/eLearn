package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.SubTopic;
import com.eLearn.eLearning.app.entity.Upload;
import com.eLearn.eLearning.app.repository.SubTopicRepository;
import com.eLearn.eLearning.app.repository.UploadRepository;
import com.eLearn.eLearning.app.response.OpResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubTopicServiceImpl implements SubTopicService {

    @Autowired
    UploadRepository uploadRepo;

    @Autowired
    SubTopicRepository subTopicRepo;

    @Override
    public OpResponse addSubTopic(SubTopic subTopic) {
        Optional<SubTopic> subTopic1= subTopicRepo.findByName(subTopic.getName());
        if(subTopic1.isEmpty()) {
            Optional<Upload> getSubTopicImage = uploadRepo.findByName(subTopic.getName().toLowerCase());
            Optional<Upload> getSubTopicVideo = uploadRepo.findByName(subTopic.getName().toLowerCase() + "vid");

            if (getSubTopicImage.isPresent() && getSubTopicVideo.isPresent()) {
                subTopic.setImage(getSubTopicImage.get().getUrl());
                subTopic.setVideo(getSubTopicVideo.get().getUrl());
                subTopicRepo.save(subTopic);
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("SubTopic added successfully")
                        .build();
            } else if (getSubTopicVideo.isPresent()) {
                subTopic.setVideo(getSubTopicVideo.get().getUrl());
                subTopicRepo.save(subTopic);
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("SubTopic added successfully")
                        .build();
            } else if (getSubTopicImage.isPresent()) {
                subTopic.setImage(getSubTopicImage.get().getUrl());
                subTopicRepo.save(subTopic);
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("SubTopic added successfully")
                        .build();
            }

            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Enter all parameters needed for subTopic")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Subtopics already exists")
                .build();
    }

    @Override
    public OpResponse getSubTopicById(Integer subTopicId) {
        Optional<SubTopic> getSubTopic= subTopicRepo.findById(subTopicId);

        if(getSubTopic.isPresent()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(getSubTopic.get())
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("SubTopic for given id"+" "+subTopicId+" "+"not present")
                .build();
    }

    @Override
    public OpResponse getByLessonId(Integer lessonid) {
        List<SubTopic> subtopic= subTopicRepo.findByLessonid(lessonid);

        if(subtopic.isEmpty()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("No data present")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(subtopic)
                .build();
    }

    @Override
    public OpResponse getAllSubTopic() {
        List<SubTopic> getAllSubTopic= subTopicRepo.findAll();

        if(getAllSubTopic.isEmpty())
            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("No subTopic available")
                    .build();

        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(getAllSubTopic)
                .build();
    }

    @Override
    public OpResponse updateSubTopic(SubTopic subTopic) {
        Optional<Upload> getSubTopicImage= uploadRepo.findByName(subTopic.getName());
        Optional<Upload> getSubTopicVideo= uploadRepo.findByName(subTopic.getName().toLowerCase()+"vid");

        if(getSubTopicImage.isPresent() && getSubTopicVideo.isPresent()){
            subTopic.setImage(getSubTopicImage.get().getUrl());
            subTopic.setVideo(getSubTopicVideo.get().getUrl());
            subTopicRepo.save(subTopic);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("SubTopic updated successfully")
                    .build();
        }

        else if(getSubTopicVideo.isPresent()){
            subTopic.setVideo(getSubTopicVideo.get().getUrl());
            subTopicRepo.save(subTopic);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("SubTopic updated successfully")
                    .build();
        }
        if(getSubTopicImage.isPresent()) {
            subTopicRepo.save(subTopic);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("SubTopic updated successfully.")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Please check all data are made accessible")
                .build();
    }

    @Override
    public OpResponse deleteSubTopicById(Integer subTopicId) {
        if(subTopicRepo.findById(subTopicId).isPresent()){
            subTopicRepo.deleteById(subTopicId);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("SubTopic of id"+" "+subTopicId+" "+"deleted successfully")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No subTopic available for given id"+" "+subTopicId)
                .build();
    }

    @Override
    public OpResponse deleteAllSubTopic() {
        if(!subTopicRepo.findAll().isEmpty()){
            subTopicRepo.deleteAll();
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("All subTopic deleted")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No subTopic present")
                .build();
    }
}
