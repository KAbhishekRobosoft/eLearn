package com.eLearn.eLearning.app.controller;
import com.eLearn.eLearning.app.entity.Completion;
import com.eLearn.eLearning.app.request.CompletionRequest;
import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.service.CompletionService;
import com.eLearn.eLearning.user.User;
import com.eLearn.eLearning.user.UserRepository;
import com.eLearn.eLearning.user.token.Token;
import com.eLearn.eLearning.user.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.eLearn.eLearning.user.Role.ADMIN;
import static com.eLearn.eLearning.user.Role.USER;

@RestController
@RequestMapping("/completion")
public class CompletionController {

    @Autowired
    CompletionService completionService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepo;

    @PostMapping("/mark")
    public OpResponse markComplete(@RequestHeader("Authorization")  String token,@RequestBody Completion completion){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent() && presentToken.get().getUserid() == completion.getUserid()) {
            Optional<User> userData = userRepo.findById(completion.getUserid());
            if (userData.get().getRole() == USER) {
                return completionService.markComplete(completion);
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

    @GetMapping("/getByUserAndLessonId")
    public OpResponse getByUserAndLessonId(@RequestHeader("Authorization")  String token,@RequestBody CompletionRequest request){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent() && presentToken.get().getUserid() == request.getUserid()) {
            Optional<User> userData = userRepo.findById(request.getUserid());
            if (userData.get().getRole() == USER) {
                return completionService.getByUserAndLessonId(request);
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
