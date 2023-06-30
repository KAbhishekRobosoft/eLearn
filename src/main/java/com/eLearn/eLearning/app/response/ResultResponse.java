package com.eLearn.eLearning.app.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResultResponse {

    private String subjectName;

    private int lessonid;

    private String subtopicName;

    private float marks;

    private float totalQuestions;

    private int percentMarks;
}
