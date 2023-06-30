package com.eLearn.eLearning.app.repository;
import com.eLearn.eLearning.app.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Likes,Integer> {
    List<Likes> findByUserid(int userid);
    Optional<Likes> findByUseridAndSubtopicid(int userid, int subtopicid);
}
