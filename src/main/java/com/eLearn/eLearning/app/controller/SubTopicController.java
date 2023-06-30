package com.eLearn.eLearning.app.controller;
import com.eLearn.eLearning.app.entity.SubTopic;
import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.service.SubTopicService;
import com.eLearn.eLearning.user.User;
import com.eLearn.eLearning.user.UserRepository;
import com.eLearn.eLearning.user.token.Token;
import com.eLearn.eLearning.user.token.TokenRepository;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.eLearn.eLearning.user.Role.ADMIN;
import static com.eLearn.eLearning.user.Role.USER;

@RestController
@RequestMapping("/subTopic")
public class SubTopicController {
    @Autowired
    SubTopicService subTopicService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepo;

    @PostMapping("/admin/add")
    public OpResponse addSubTopic(@RequestHeader("Authorization")  String token ,@RequestBody SubTopic subTopic){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return subTopicService.addSubTopic(subTopic);
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

    @GetMapping("/getById/{subTopicId}")
    public OpResponse getSubTopicById(@RequestHeader("Authorization")  String token,@PathVariable Integer subTopicId){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == USER) {
                return subTopicService.getSubTopicById(subTopicId);
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
    public OpResponse getAllSubTopic(@RequestHeader("Authorization")  String token){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return subTopicService.getAllSubTopic();
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
    public OpResponse updateSubTopic(@RequestHeader("Authorization")  String token,@RequestBody SubTopic subTopic){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return subTopicService.updateSubTopic(subTopic);
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

    @DeleteMapping("/admin/deleteById/{subTopicId}")
    public OpResponse deleteSubTopicById(@RequestHeader("Authorization")  String token,@PathVariable Integer subTopicId){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return subTopicService.deleteSubTopicById(subTopicId);
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

    @DeleteMapping("/admin/deleteAll")
    public OpResponse deleteAllSubTopic(@RequestHeader("Authorization")  String token){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return subTopicService.deleteAllSubTopic();
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
