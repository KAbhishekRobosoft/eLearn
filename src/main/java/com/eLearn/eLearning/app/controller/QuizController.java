package com.eLearn.eLearning.app.controller;
import com.eLearn.eLearning.app.entity.Quiz;
import com.eLearn.eLearning.app.request.AnswerRequest;
import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.service.QuizService;
import com.eLearn.eLearning.user.Role;
import com.eLearn.eLearning.user.User;
import com.eLearn.eLearning.user.UserRepository;
import com.eLearn.eLearning.user.token.Token;
import com.eLearn.eLearning.user.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.eLearn.eLearning.user.Role.USER;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepo;

    @PostMapping("/admin/create")
    public OpResponse create(@RequestHeader("Authorization")  String token,@RequestBody Quiz quiz){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent() && presentToken.get().getUserid() == quiz.getUserid()) {
            Optional<User> userData = userRepo.findById(quiz.getUserid());
            if (userData.get().getRole() == Role.ADMIN) {
                return quizService.create(quiz);
            }
            return OpResponse.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("Only admins can access this")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Admin does not exist")
                .build();
    }

    @GetMapping("/get/{sectionId}")
    public OpResponse getBySectionId(@RequestHeader("Authorization")  String token,@PathVariable Integer sectionId){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == USER) {
                return quizService.getBySectionId(sectionId);
            }
            return OpResponse.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("Only users can access this")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("User does not exist")
                .build();
    }

    @GetMapping("/admin/getAll")
    public OpResponse getAllQuiz(@RequestHeader("Authorization")  String token){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == Role.ADMIN) {
                return quizService.getAllQuiz();
            }
            return OpResponse.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("Only admins can access this")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Admin does not exist")
                .build();
    }

    @PutMapping("/admin/update")
    public OpResponse update(@RequestHeader("Authorization")  String token,@RequestBody Quiz quiz){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent() && presentToken.get().getUserid() == quiz.getUserid()) {
            Optional<User> userData = userRepo.findById(quiz.getUserid());
            if (userData.get().getRole() == Role.ADMIN) {
                return quizService.update(quiz);
            }
            return OpResponse.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("Only admins can access this")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Admin does not exist")
                .build();
    }

    @DeleteMapping("/admin/deleteById/{quizId}")
    public OpResponse deleteById(@RequestHeader("Authorization")  String token,@PathVariable Integer quizId){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == Role.ADMIN) {
                return quizService.deleteById(quizId);
            }
            return OpResponse.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("Only admins can access this")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("Admin does not exist")
                .build();
    }

    @GetMapping("/getResults")
    public OpResponse checkAnswers(@RequestHeader("Authorization")  String token,@RequestBody AnswerRequest request){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent() && presentToken.get().getUserid() == request.getUserid()) {
            Optional<User> userData = userRepo.findById(request.getUserid());
            if (userData.get().getRole() == USER) {
                return quizService.checkAnswers(request);
            }
            return OpResponse.builder()
                    .statusCode(HttpStatus.UNAUTHORIZED.value())
                    .message("Only users can access this")
                    .build();
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("User does not exist")
                .build();
    }

}
