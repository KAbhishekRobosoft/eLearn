package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.SubTopic;
import com.eLearn.eLearning.app.response.OpResponse;

public interface SubTopicService {
    public OpResponse addSubTopic(SubTopic subTopic);

    public OpResponse getSubTopicById(Integer subTopicId);

    public OpResponse getByLessonId(Integer lessonid);


    public OpResponse getAllSubTopic();

    public OpResponse updateSubTopic(SubTopic subTopic);

    public OpResponse deleteSubTopicById(Integer subTopicId);

    public OpResponse deleteAllSubTopic();
}
