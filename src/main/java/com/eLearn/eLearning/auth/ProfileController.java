package com.eLearn.eLearning.auth;
import com.eLearn.eLearning.app.entity.*;
import com.eLearn.eLearning.app.repository.*;
import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.app.response.ProfileResponse;
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
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    CompletionRepository completionRepo;

    @Autowired
    UploadRepository uploadRepo;

    @Autowired
    SubTopicRepository subTopicRepo;

    @Autowired
    QuizRepository quizRepo;

    @Autowired
    ResultRepository resultRepo;

    @Autowired
    TokenRepository tokenRepository;


    private float sum= 0;

    private float max= 0;

    private int percentMark= 0;

    @GetMapping("/getProfile/{userid}")
    public OpResponse getProfile(@RequestHeader("Authorization")  String token, @PathVariable int userid) {
        token = token.split(" ")[1];
        Optional<Token> presentToken = tokenRepository.findByToken(token);
        if (presentToken.isPresent() && presentToken.get().getUserid() == userid) {
            Optional<User> userData = userRepo.findById(presentToken.get().getUserid());
            if (userData.get().getRole() == USER) {
                Optional<User> user = userRepo.findById(userid);
                if (user.isPresent()) {
                    ProfileResponse profileResponse = new ProfileResponse();
                    List<SubTopic> subTopicList = subTopicRepo.findAll();
                    List<Completion> completionList = completionRepo.findByUserid(userid);
                    List<Result> resultList = resultRepo.findByUserid(userid);
                    profileResponse.setName(user.get().getName());
                    profileResponse.setEmail(user.get().getEmail());
                    Optional<Upload> uploadData = uploadRepo.findByName(user.get().getName());
                    if (uploadData.isPresent())
                        profileResponse.setImage(uploadData.get().getUrl());
                    else {
                        profileResponse.setImage(uploadRepo.findByName("user").get().getUrl());
                    }
                    if (subTopicList.size() > 0) {
                        sum = 0;
                        resultList.forEach(ele -> {
                            sum += ele.getMarks();
                            if (max < ele.getMarks()) {
                                max = ele.getMarks();
                                Optional<Quiz> quizData = quizRepo.findById(ele.getQuizid());
                                percentMark = (int) (max * 100) / (quizData.get().getQuestions().size());
                            }
                        });
                        profileResponse.setAvgScore((sum / resultList.size()));
                        profileResponse.setPercentComplete((completionList.size() * 100) / subTopicList.size());
                        profileResponse.setPercentHighScore(percentMark);
                        return OpResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message(profileResponse)
                                .build();
                    } else {
                        profileResponse.setAvgScore(0);
                        profileResponse.setPercentComplete(0);
                        profileResponse.setPercentHighScore(0);
                        return OpResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message(profileResponse)
                                .build();
                    }
                }

                return OpResponse.builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("User does not exist")
                        .build();
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
