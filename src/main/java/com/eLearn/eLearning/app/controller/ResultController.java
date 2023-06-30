package com.eLearn.eLearning.app.controller;

import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.service.ResultService;
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
@RequestMapping("/result")
public class ResultController {

    @Autowired
    ResultService resultService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/getByUserId/{userid}")
    public OpResponse getByUserId(@RequestHeader("Authorization")  String token,@PathVariable int userid){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == USER) {
                return resultService.getByUserId(userid);
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

    @DeleteMapping("/admin/delete/{resultid}")
    public OpResponse deleteById(@RequestHeader("Authorization")  String token,@PathVariable int resultid){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return resultService.deleteById(resultid);
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
