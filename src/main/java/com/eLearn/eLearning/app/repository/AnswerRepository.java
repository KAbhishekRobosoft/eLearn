package com.eLearn.eLearning.app.repository;

import com.eLearn.eLearning.app.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Integer> {
    Optional<Answer> findByQuestionid(Integer questionId);
}
