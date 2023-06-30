package com.eLearn.eLearning.app.repository;

import com.eLearn.eLearning.app.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section,Integer> {
    public Optional<Section> findByName(String sectionName);
}
