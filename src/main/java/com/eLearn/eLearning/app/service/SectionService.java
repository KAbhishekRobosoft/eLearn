package com.eLearn.eLearning.app.service;

import com.eLearn.eLearning.app.entity.Section;
import com.eLearn.eLearning.app.response.OpResponse;

public interface SectionService {

    public OpResponse addSection(Section section);

    public OpResponse getSectionById(Integer sectionId);

    public OpResponse getAllSection();

    public OpResponse updateSection(Section section);

    public OpResponse deleteSectionById(Integer sectionId);
}
