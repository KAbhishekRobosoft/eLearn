package com.eLearn.eLearning.app.service;

import com.eLearn.eLearning.app.entity.*;
import com.eLearn.eLearning.app.repository.*;
import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.response.ResultResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ResultServiceImpl implements ResultService {

    @Autowired
    ResultRepository resultRepo;

    @Autowired
    QuizRepository quizRepo;

    @Autowired
    SubTopicRepository subTopicRepo;

    @Autowired
    SectionRepository sectionRepo;

    @Autowired
    SubjectRepository subjectRepo;

    @Override
    public OpResponse getByUserId(int userid) {
        List<Result> results= resultRepo.findByUserid(userid);
        if(!results.isEmpty()) {
            List<ResultResponse> resultResponses = new ArrayList<>();
            results.forEach(ele -> {
                ResultResponse response = new ResultResponse();
                Optional<Quiz> quiz = quizRepo.findById(ele.getQuizid());
                Optional<SubTopic> subTopic = subTopicRepo.findById(quiz.get().getSubtopicid());
                Optional<Section> section = sectionRepo.findById(quiz.get().getSectionid());
                Optional<Subject> subject = subjectRepo.findById(section.get().getSubjectid());
                response.setSubjectName(subject.get().getName());
                response.setSubtopicName(subTopic.get().getName());
                response.setLessonid(subTopic.get().getLessonid());
                response.setTotalQuestions(ele.getTotalmarks());
                response.setMarks(ele.getMarks());
                response.setPercentMarks(ele.getPercentage());
                resultResponses.add(response);
            });
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(resultResponses)
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No quiz attended by user yet")
                .build();

    }

    @Override
    public OpResponse deleteById(int resultid) {
        Optional<Result> result= resultRepo.findById(resultid);
        if(result.isPresent()){
            resultRepo.deleteById(resultid);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Result with id"+" "+resultid+" "+"deleted successfully")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Result with id"+" "+resultid+" "+"does not exist")
                .build();
    }
}
