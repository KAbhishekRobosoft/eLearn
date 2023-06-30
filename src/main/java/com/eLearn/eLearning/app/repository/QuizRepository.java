package com.eLearn.eLearning.app.repository;

import com.eLearn.eLearning.app.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {
    Optional<Quiz> findByName(String name);

    List<Quiz> findBySectionid(Integer sectionId);

}
