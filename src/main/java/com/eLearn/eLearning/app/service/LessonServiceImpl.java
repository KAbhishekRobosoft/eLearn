package com.eLearn.eLearning.app.service;

import com.eLearn.eLearning.app.entity.Lesson;
import com.eLearn.eLearning.app.entity.Subject;
import com.eLearn.eLearning.app.entity.Upload;
import com.eLearn.eLearning.app.repository.LessonRepository;
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
public class LessonServiceImpl implements LessonService {

    @Autowired
    UploadRepository uploadRepo;

    @Autowired
    LessonRepository lessonRepo;

    @Override
    public OpResponse addLesson(Lesson lesson) {
        Optional<Lesson> lesson1= lessonRepo.findByName(lesson.getName());
        if(lesson1.isEmpty()) {
            Optional<Upload> getLessonImage = uploadRepo.findByName(lesson.getName().toLowerCase());
            if (getLessonImage.isPresent()) {
                lesson.setImage(getLessonImage.get().getUrl());
                lessonRepo.save(lesson);
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Lesson added successfully")
                        .build();
            }

            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Enter all parameters needed for lesson")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Lesson already exists")
                .build();
    }

    @Override
    public OpResponse getLessonById(Integer lessonId) {
        Optional<Lesson> getLesson= lessonRepo.findById(lessonId);

        if(getLesson.isPresent()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(getLesson.get())
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Lesson for given id"+" "+lessonId+" "+"not present")
                .build();
    }

    @Override
    public OpResponse getAllLesson() {
        List<Lesson> getAllLesson= lessonRepo.findAll();

        if(getAllLesson.isEmpty())
            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("No lesson available")
                    .build();

        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(getAllLesson)
                .build();
    }

    @Override
    public OpResponse getLessonByName(String lessonName) {
        Optional<Lesson> getLesson= lessonRepo.findByName(lessonName);

        if(getLesson.isPresent()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(getLesson.get())
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Lesson not present for the given name"+" "+lessonName+".")
                .build();
    }

    @Override
    public OpResponse updateLesson(Lesson lesson) {
        Optional<Upload> getImage= uploadRepo.findByName(lesson.getName());
        if(getImage.isPresent()) {
            lessonRepo.save(lesson);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(lesson)
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Please check all data are made accessible")
                .build();
    }

    @Override
    public OpResponse deleteLessonById(Integer lessonId) {
        if(lessonRepo.findById(lessonId).isPresent()){
            lessonRepo.deleteById(lessonId);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Lesson of id"+" "+lessonId+" "+"deleted successfully")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No lesson available for given id"+" "+lessonId)
                .build();
    }

    @Override
    public OpResponse deleteAllLesson() {
        if(!lessonRepo.findAll().isEmpty()){
            lessonRepo.deleteAll();
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("All lessons deleted")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No lessons present")
                .build();
    }
}
