package com.eLearn.eLearning.app.controller;
import com.eLearn.eLearning.app.request.LikeRequest;
import com.eLearn.eLearning.app.response.LikeResponse;
import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.service.LikeService;
import com.eLearn.eLearning.user.User;
import com.eLearn.eLearning.user.UserRepository;
import com.eLearn.eLearning.user.token.Token;
import com.eLearn.eLearning.user.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static com.eLearn.eLearning.user.Role.USER;

@RestController
@RequestMapping("/like")
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepo;

    @PostMapping("/operation")
    public OpResponse likeOperation(@RequestHeader("Authorization") String token,@RequestBody LikeRequest request){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent() && presentToken.get().getUserid() == request.getUserid()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == USER) {
                return likeService.likeOperation(request);
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

    @GetMapping("/checkLike")
    public OpResponse getByUserAndSubtopicid(@RequestHeader("Authorization")  String token,@RequestBody LikeRequest request){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent() && presentToken.get().getUserid() == request.getUserid()) {
            Optional<User> userData = userRepo.findById(request.getUserid());
            if (userData.get().getRole() == USER) {
                return likeService.getByUserAndSubtopicid(request);
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

    @GetMapping("/getLikes/{userid}")
    public OpResponse getByUserId(@RequestHeader("Authorization")  String token,@PathVariable int userid){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent() && presentToken.get().getUserid() == userid) {
            Optional<User> userData = userRepo.findById(userid);
            if (userData.get().getRole() == USER) {
                return likeService.getByUserId(userid);
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
