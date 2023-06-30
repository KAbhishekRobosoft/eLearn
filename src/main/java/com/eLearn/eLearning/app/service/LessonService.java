package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.Lesson;
import com.eLearn.eLearning.app.response.OpResponse;

public interface LessonService {
    public OpResponse addLesson(Lesson lesson);

    public OpResponse getLessonById(Integer lessonId);

    public OpResponse getAllLesson();

    public OpResponse getLessonByName(String lessonName);

    public OpResponse updateLesson(Lesson lesson);

    public OpResponse deleteLessonById(Integer sectionId);

    public OpResponse deleteAllLesson();
}
