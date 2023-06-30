package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.Subject;
import com.eLearn.eLearning.app.entity.Upload;
import com.eLearn.eLearning.app.repository.SubjectRepository;
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
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepo;

    @Autowired
    UploadRepository uploadRepo;


    @Override
    public OpResponse addSubject(Subject subject) {
        Optional<Subject> subject1= subjectRepo.findByName(subject.getName());
        if(subject1.isEmpty()) {
            Optional<Upload> getSubjectImage = uploadRepo.findByName(subject.getName().toLowerCase());
            if (getSubjectImage.isPresent()) {
                subject.setImage(getSubjectImage.get().getUrl());
                subjectRepo.save(subject);
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Subject added successfully")
                        .build();
            }

            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Enter all parameters needed for subject")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Subject already exists")
                .build();
    }

    @Override
    public OpResponse getSubjectById(Integer subjectId) {
        Optional<Subject> getSubject= subjectRepo.findById(subjectId);

        if(getSubject.isPresent()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(getSubject.get())
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Subject for given id"+" "+subjectId+" "+"not present")
                .build();
    }

    @Override
    public OpResponse getAllSubject() {
        List<Subject> getAllSubject= subjectRepo.findAll();

        if(getAllSubject.isEmpty())
            return OpResponse.builder()
                             .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                             .message("No subjects available")
                             .build();

        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(getAllSubject)
                .build();
    }

    @Override
    public OpResponse getSubjectByName(String subjectName) {
        Optional<Subject> getSubject= subjectRepo.findByName(subjectName);

        if(getSubject.isPresent()){
            return OpResponse.builder()
                             .statusCode(HttpStatus.OK.value())
                             .message(getSubject.get())
                             .build();
        }

        return OpResponse.builder()
                         .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                         .message("Subject not present for the given name"+" "+subjectName+".")
                         .build();
    }

    @Override
    public OpResponse updateSubject(Subject subject) {
        Optional<Upload> getImage= uploadRepo.findByName(subject.getName());
        if(getImage.isPresent()) {
            subjectRepo.save(subject);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(subject)
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Please check all data are made accessible")
                .build();
    }

    @Override
    public OpResponse deleteSubjectById(Integer subjectId) {
        if(subjectRepo.findById(subjectId).isPresent()){
            subjectRepo.deleteById(subjectId);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Subject of id"+" "+subjectId+" "+"deleted successfully")
                    .build();
        }

        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No subject available for given id"+" "+subjectId)
                .build();
    }
}
