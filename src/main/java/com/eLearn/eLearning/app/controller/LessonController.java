package com.eLearn.eLearning.app.controller;
import com.eLearn.eLearning.app.entity.Lesson;
import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.service.LessonService;
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
@RequestMapping("/lesson")
public class LessonController {
    @Autowired
    LessonService lessonService;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepo;

    @PostMapping("/admin/add")
    public OpResponse addLesson(@RequestHeader("Authorization")  String token,@RequestBody Lesson lesson){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return lessonService.addLesson(lesson);
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

    @GetMapping("/getById/{lessonId}")
    public OpResponse getLessonById(@RequestHeader("Authorization")  String token,@PathVariable Integer lessonId){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == USER) {
                return lessonService.getLessonById(lessonId);
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
    public OpResponse getAllLesson(@RequestHeader("Authorization")  String token){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return lessonService.getAllLesson();
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
    public OpResponse updateLesson(@RequestHeader("Authorization")  String token,@RequestBody Lesson lesson){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return lessonService.updateLesson(lesson);
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

    @DeleteMapping("/admin/deleteById/{lessonId}")
    public OpResponse deleteLessonById(@RequestHeader("Authorization")  String token,@PathVariable Integer lessonId){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return lessonService.deleteLessonById(lessonId);
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
    public OpResponse deleteAllLesson(@RequestHeader("Authorization")  String token){
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == ADMIN) {
                return lessonService.deleteAllLesson();
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
