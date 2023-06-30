package com.eLearn.eLearning.app.repository;

import com.eLearn.eLearning.app.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Integer> {
    Optional<Lesson> findByName(String lessonName);
}
