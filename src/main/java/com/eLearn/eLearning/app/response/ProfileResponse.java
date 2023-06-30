package com.eLearn.eLearning.app.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String email;

    private String name;

    private String image;

    private int percentComplete;

    private float AvgScore;

    private int percentHighScore;



}
