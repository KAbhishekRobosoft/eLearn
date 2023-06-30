package com.eLearn.eLearning.app.repository;
import com.eLearn.eLearning.app.entity.Completion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompletionRepository extends JpaRepository<Completion,Integer> {
    List<Completion> findByUserid(int userid);
    List<Completion> findByUseridAndLessonid(int userid,int lessonid);

    Optional<Completion> findByUseridAndSubtopicid(int userid, int subtopicid);
}
