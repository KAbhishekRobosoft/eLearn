package com.eLearn.eLearning.app.otp;

import com.eLearn.eLearning.user.User;
import com.eLearn.eLearning.user.UserRepository;
import com.eLearn.eLearning.user.token.Token;
import com.eLearn.eLearning.user.token.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Optional;

@RestController
@RequestMapping("/otp")
public class OTPController {


    @Autowired
    public OTPService otpService;


    @Autowired
    UserRepository userRepo;

    @Autowired
    TokenRepository tokenRepo;

    @GetMapping("/generateOtp")
    public ResponseEntity<?> generateOTP(@RequestHeader("Authorization") String token,@RequestBody OTPRequest request) throws MessagingException {
        Optional<User> user= userRepo.findByEmail(request.getEmail());
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepo.findByToken(token);
        if(presentToken.isPresent()) {
            if (user.isPresent()) {
                int otp = otpService.generateOTP(user.get().getEmail());
                OTPGetResponse otpGetResponse= new OTPGetResponse(otp,request.getEmail());
                return ResponseEntity.ok(otpGetResponse);
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
        public ResponseEntity<?> validateOtp(@RequestHeader("Authorization") String token,@RequestBody OTPGetResponse request) {

        final String SUCCESS = "Entered Otp is valid";
        final String FAIL = "Entered Otp is NOT valid. Please Retry!";
        Optional<User> user = userRepo.findByEmail(request.getEmail());
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepo.findByToken(token);
        if(presentToken.isPresent()) {
            if (user.isPresent()) {
                int otpnum = request.getOtp();
                if (otpnum >= 0) {
                    int serverOtp = otpService.getOtp(request.getEmail());
                    if (serverOtp > 0) {
                        if (otpnum == serverOtp) {
                            otpService.clearOTP(request.getEmail());
                            OTPValidationResponse response = new OTPValidationResponse("success");
                            return ResponseEntity.ok(response);
                        } else {
                            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                        }
                    } else {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}