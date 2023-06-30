package com.eLearn.eLearning.app.service;

import com.eLearn.eLearning.app.entity.Subject;
import com.eLearn.eLearning.app.response.OpResponse;

public interface SubjectService {
    public OpResponse addSubject(Subject subject);

    public OpResponse getSubjectById(Integer subjectId);

    public OpResponse getAllSubject();

    public OpResponse getSubjectByName(String subjectName);

    public OpResponse updateSubject(Subject subject);

    public OpResponse deleteSubjectById(Integer subjectId);

}
