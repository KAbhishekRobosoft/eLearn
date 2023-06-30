package com.eLearn.eLearning.app.controller;

import com.eLearn.eLearning.app.entity.Answer;
import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.service.AnswerService;
import com.eLearn.eLearning.user.User;
import com.eLearn.eLearning.user.UserRepository;
import com.eLearn.eLearning.user.token.Token;
import com.eLearn.eLearning.user.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.eLearn.eLearning.user.Role.ADMIN;

@RestController
@RequestMapping("/answer")
public class AnswerController {

    @Autowired
    AnswerService answerService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepo;

    @PostMapping("/admin/create")
    public OpResponse create(@RequestHeader("Authorization")  String token, @RequestBody Answer answer){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return answerService.create(answer);
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
    public OpResponse updateByQuestionId(@RequestHeader("Authorization")  String token,@RequestBody Answer answerObj){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return answerService.updateByQuestionId(answerObj);
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

}
