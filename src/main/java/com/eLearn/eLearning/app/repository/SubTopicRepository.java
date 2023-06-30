package com.eLearn.eLearning.app.repository;
import com.eLearn.eLearning.app.entity.SubTopic;
import com.eLearn.eLearning.app.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubTopicRepository extends JpaRepository<SubTopic,Integer> {
    public Optional<SubTopic> findByName(String subTopic);

    public List<SubTopic> findByLessonid(int lessonid);

}
