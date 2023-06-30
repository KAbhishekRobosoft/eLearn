package com.eLearn.eLearning.app.controller;
import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.service.UploadService;
import com.eLearn.eLearning.user.token.Token;
import com.eLearn.eLearning.user.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @Autowired
    TokenRepository tokenRepository;

    @PostMapping("/image")
    public OpResponse uploadImage(@RequestHeader("Authorization")  String token,@RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException {
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            return uploadService.uploadImage(file, name.toLowerCase());
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("User does not exist")
                .build();
    }

    @PostMapping("/video")
    public OpResponse uploadVideo(@RequestHeader("Authorization")  String token, @RequestParam("file") MultipartFile file, @RequestParam("name") String name) throws IOException {
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
           return uploadService.uploadVideo(file, name.toLowerCase());
        }
        return OpResponse.builder()
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .message("User does not exist")
                .build();
    }


}
