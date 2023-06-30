package com.eLearn.eLearning.app.service;
import com.eLearn.eLearning.app.entity.Answer;
import com.eLearn.eLearning.app.entity.Question;
import com.eLearn.eLearning.app.entity.Quiz;
import com.eLearn.eLearning.app.entity.Result;
import com.eLearn.eLearning.app.repository.QuestionRepository;
import com.eLearn.eLearning.app.repository.QuizRepository;
import com.eLearn.eLearning.app.repository.ResultRepository;
import com.eLearn.eLearning.app.request.AnswerFormat;
import com.eLearn.eLearning.app.request.AnswerRequest;
import com.eLearn.eLearning.app.response.OpResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

    @Autowired
    QuizRepository quizRepo;

    @Autowired
    QuestionRepository questionRepo;

    @Autowired
    ResultRepository resultRepo;

    @Override
    public OpResponse create(Quiz quiz) {
        Optional<Quiz> quizData= quizRepo.findByName(quiz.getName());

        if(quizData.isEmpty()){
            quiz.setCreatedDate(LocalDateTime.now().toString());
            quizRepo.save(quiz);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(quiz)
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Quiz already created")
                .build();
    }

    @Override
    public OpResponse getBySectionId(Integer sectionId) {
        List<Quiz> quizData= quizRepo.findBySectionid(sectionId);

        if(quizData.size() > 0){
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message(quizData)
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No quizes available")
                .build();

    }

    @Override
    public OpResponse getAllQuiz() {
        List<Quiz> quizData= quizRepo.findAll();
        if(quizData.isEmpty()){
            return OpResponse.builder()
                    .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("No quizes available")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message(quizData)
                .build();
    }

    @Override
    public OpResponse update(Quiz quiz) {
        quiz.setCreatedDate(LocalDateTime.now().toString());
        quizRepo.save(quiz);
        return OpResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Updated successfully")
                .build();
    }

    @Override
    public OpResponse deleteById(Integer quizId) {
        if(quizRepo.findById(quizId).isPresent()){
            quizRepo.deleteById(quizId);
            return OpResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Id with"+" "+quizId+" "+"deleted successfully")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("No quiz available")
                .build();
    }

    @Override
    public OpResponse checkAnswers(AnswerRequest request) {
        Optional<Quiz> quiz = quizRepo.findById(request.getQuizid());
        Optional<Result> resultData= resultRepo.findByQuizidAndUserid(request.getQuizid(),request.getUserid());
        int marks = quiz.get().getQuestions().size();
        List<AnswerFormat> answers = request.getAnswers();
        if (answers.size() > 0) {
            if (answers.size() < marks) {
                marks = marks - (marks - answers.size());
            }
            for (AnswerFormat answerFormat : answers) {
                Optional<Question> question = questionRepo.findById(answerFormat.getQuestionid());
                Answer answer = question.get().getCorrectAnswer();
                    if (answer.getText().equalsIgnoreCase(answerFormat.getOption())) {
                        continue;
                    }
                    else {
                        marks -= 1;
                    }
            }
            Result result= new Result();
            result.setUserid(request.getUserid());
            result.setQuizid(request.getQuizid());
            result.setTotalmarks(quiz.get().getQuestions().size());
            result.setMarks(marks);
            result.setPercentage((100 * marks)/(quiz.get().getQuestions().size()));
            if(result.getPercentage() > 70){
                result.setStatus("Passed");
            }
            else{
                result.setStatus("Failed");
            }

            if(resultData.isPresent()){
                resultRepo.deleteById(resultData.get().getId());
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(resultRepo.save(result))
                        .build();
            }
                return OpResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message(resultRepo.save(result))
                        .build();

        }
        return OpResponse.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("Send required parameters properly")
                .build();
    }

}
