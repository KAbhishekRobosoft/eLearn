package com.eLearn.eLearning.app.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerRequest {

    private int userid;

    private int quizid;

    private List<AnswerFormat> answers= new ArrayList<>();
}
