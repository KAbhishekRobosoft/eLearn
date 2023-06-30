package com.eLearn.eLearning.app.response;

import com.eLearn.eLearning.app.entity.Completion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompletionResponse {

    private List<Completion> completion;
    private int percentCompletion;
}
