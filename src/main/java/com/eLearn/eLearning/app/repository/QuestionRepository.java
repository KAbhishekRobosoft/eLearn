package com.eLearn.eLearning.app.repository;

import com.eLearn.eLearning.app.entity.Question;
import com.eLearn.eLearning.app.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Optional<Question> findByQuestion(String name);

    List<Question> findByQuizid(Integer quizId);
}
