package com.eLearn.eLearning.auth;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private int userId;

    private String email;

    private String token;

    private String refreshtoken;
}
