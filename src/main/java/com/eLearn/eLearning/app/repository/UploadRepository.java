package com.eLearn.eLearning.app.repository;

import com.eLearn.eLearning.app.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadRepository extends JpaRepository<Upload,Integer> {
    Optional<Upload> findByName(String name);
}
