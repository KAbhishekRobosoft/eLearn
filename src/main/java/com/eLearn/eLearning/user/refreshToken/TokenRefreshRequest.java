package com.eLearn.eLearning.user.refreshToken;
import lombok.NonNull;


public class TokenRefreshRequest {
    @NonNull
    private String refreshToken;


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public TokenRefreshRequest(String refreshToken, int userId) {
        this.refreshToken = refreshToken;

    }
}
