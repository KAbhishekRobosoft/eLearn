package com.eLearn.eLearning.app.repository;
import com.eLearn.eLearning.app.entity.Quiz;
import com.eLearn.eLearning.app.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResultRepository extends JpaRepository<Result, Integer> {
        List<Result> findByUserid(int userId);

        Optional<Result> findByQuizid(int quizid);

        Optional<Result> findByQuizidAndUserid(int quizid, int userid);
}
