package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.Section;
import com.eLearn.eLearning.app.entity.Upload;
import com.eLearn.eLearning.app.repository.SectionRepository;
import com.eLearn.eLearning.app.repository.UploadRepository;
import com.eLearn.eLearning.app.response.OpResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    @Autowired
    SectionRepository sectionRepo;

    @Autowired
    UploadRepository uploadRepo;

    @Override
    public OpResponse addSection(Section section) {

        Optional<Upload> getSectionImage= uploadRepo.findByName(section.getName().toLowerCase());
        Optional<Section> section1= sectionRepo.findByName(section.getName());
        if(section1.isEmpty()) {
            if (getSectionImage.isPresent()) {
                section.setImage(getSectionImage.get().getUrl());
                sectionRepo.save(section);
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Section added successfully")
                        .build();
            }

            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Enter all parameters needed for subject")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Section already present")
                .build();
    }

    @Override
    public OpResponse getSectionById(Integer sectionId) {
        Optional<Section> getSection= sectionRepo.findById(sectionId);

        if(getSection.isPresent()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(getSection.get())
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Section for given id"+" "+sectionId+" "+"not present")
                .build();
    }

    @Override
    public OpResponse getAllSection() {
        List<Section> getAllSection= sectionRepo.findAll();

        if(getAllSection.isEmpty())
            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("No sections available")
                    .build();

        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(getAllSection)
                .build();
    }

    @Override
    public OpResponse updateSection(Section section) {
        Optional<Upload> getImage= uploadRepo.findByName(section.getName());
        if(getImage.isPresent()) {
            sectionRepo.save(section);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(section)
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Please check all data are made accessible")
                .build();
    }

    @Override
    public OpResponse deleteSectionById(Integer sectionId) {
        if(sectionRepo.findById(sectionId).isPresent()){
            sectionRepo.deleteById(sectionId);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Section of id"+" "+sectionId+" "+"deleted successfully")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No section available for given id"+" "+sectionId)
                .build();
    }
}
