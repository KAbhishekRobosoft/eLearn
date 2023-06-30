package com.eLearn.eLearning.auth;

import com.eLearn.eLearning.user.refreshToken.TokenRefreshRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/auth/registerUser")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/auth/registerAdmin")
    public ResponseEntity<RegisterResponse> registerAdmin(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.registerAdmin(request));
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }

    @PostMapping("/auth/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody TokenRefreshRequest request){
        return ResponseEntity.ok(authService.refreshToken(request));
    }

    @PutMapping("/auth/update")
    public ResponseEntity<?> update(@RequestHeader ("Authorization") String token,@RequestBody UserUpdateRequest request){
        return ResponseEntity.ok(authService.update(token,request));
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestHeader("Authorization") String token, @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(authService.resetPassword(token,request));
    }

    @PostMapping("/signOut")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token,@RequestBody LogoutRequest request) {
        return ResponseEntity.ok(authService.logout(token,request));
    }
}
