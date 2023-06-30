package com.eLearn.eLearning.auth;

import com.eLearn.eLearning.app.response.OpResponse;
import com.eLearn.eLearning.config.JwtService;
import com.eLearn.eLearning.user.Role;
import com.eLearn.eLearning.user.User;
import com.eLearn.eLearning.user.UserRepository;
import com.eLearn.eLearning.user.refreshToken.RefreshTokenService;
import com.eLearn.eLearning.user.refreshToken.TokenRefreshRequest;
import com.eLearn.eLearning.user.token.Token;
import com.eLearn.eLearning.user.token.TokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.eLearn.eLearning.user.refreshToken.*;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    private final UserRepository userRepo;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final RefreshTokenService refreshTokenService;

    private final TokenRepository tokenRepository;

    private final RefreshTokenRepository refreshRepo;

    public RegisterResponse registerUser(RegisterRequest request) {
        Optional<User> userInfo= userRepo.findByEmail(request.getEmail());

        if(userInfo.isEmpty() && Objects.equals(request.getPassword(), request.getConfirmpassword())) {
            var user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .confirmpassword(passwordEncoder.encode(request.getConfirmpassword()))
                    .role(Role.USER)
                    .build();

            userRepo.save(user);
            return RegisterResponse
                    .builder()
                    .message("Account" + " " + request.getEmail() + " " + "registered successfully.")
                    .build();
        }

        return  RegisterResponse
                .builder()
                .message("Some error has occurred")
                .build();
    }

    public RegisterResponse registerAdmin(RegisterRequest request) {
        Optional<User> userInfo= userRepo.findByEmail(request.getEmail());
        if(userInfo.isEmpty() && Objects.equals(request.getPassword(), request.getConfirmpassword())) {
            var user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .confirmpassword(passwordEncoder.encode(request.getConfirmpassword()))
                    .role(Role.ADMIN)
                    .build();

            userRepo.save(user);
            return RegisterResponse
                    .builder()
                    .message("Account" + " " + request.getEmail() + " " + "registered successfully.")
                    .build();
        }
        return  RegisterResponse
                .builder()
                .message("Some error has occured")
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user= userRepo.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken= jwtService.generateToken(user);
        String refreshToken = refreshTokenService.createRefreshToken(user.getId());
        Optional<Token> token= tokenRepository.findByUserId(user.getId());

        if(token.isPresent()){
            token.get().setToken(jwtToken);
            token.get().setUserid(user.getId());
            tokenRepository.save(token.get());
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .refreshtoken(refreshToken)
                    .userId(user.getId())
                    .email(user.getEmail())
                    .build();
        }

        Token newToken= new Token();
        newToken.setUserid(user.getId());
        newToken.setToken(jwtToken);
        tokenRepository.save(newToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .userId(user.getId())
                .email(user.getEmail())
                .refreshtoken(refreshToken)
                .build();
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request){
        String requestRefreshToken = request.getRefreshToken();
        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String refreshToken = jwtService.generateToken(user);
                    Optional<Token> presentToken= tokenRepository.findByUserId(user.getId());
                    presentToken.ifPresent(token -> {
                        presentToken.get().setToken(refreshToken);
                        tokenRepository.save(presentToken.get());
                    });

                    return ResponseEntity.ok(new TokenRefreshResponse(refreshToken,requestRefreshToken));
                })
                .orElseThrow(() -> new RuntimeException("Token not present in database"));
    }

    public ResponseEntity<?> resetPassword(String token,ResetPasswordRequest request) {
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        Optional<User> userInfo = userRepo.findByEmail(request.getEmail());

        if(presentToken.isPresent()) {
            if (userInfo.isPresent()) {
                userInfo.get().setPassword(passwordEncoder.encode(request.getNewPassword()));
                User userData = userRepo.save(userInfo.get());
                ResetPasswordResponse response = new ResetPasswordResponse("success");
                return ResponseEntity.ok(response);
            } else {
                throw new RuntimeException("User does not exist");
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> update(String token,UserUpdateRequest request){
        Optional<User> user= userRepo.findById(request.getUserId());
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            if (user.isPresent()) {
                user.get().setName(request.getName());
                user.get().setEmail(request.getEmail());
                return ResponseEntity.ok(userRepo.save(user.get()));
            }
            return (ResponseEntity<?>) ResponseEntity.internalServerError();
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<?> logout(String token,LogoutRequest request) {
        Optional<User> userInfo = userRepo.findByEmail(request.getEmail());
        token = token.split(" ")[1];
        Optional<Token> presentToken= tokenRepository.findByToken(token);
        if(presentToken.isPresent()) {
            if (userInfo.isPresent()) {
                tokenRepository.deleteByToken(token);
                refreshRepo.deleteAllByUserid(userInfo.get().getId());
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new RuntimeException("User does not exist");
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }
}
