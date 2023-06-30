package com.eLearn.eLearning.app.repository;

import com.eLearn.eLearning.app.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    public Optional<Subject> findByName(String subjectName);

}
