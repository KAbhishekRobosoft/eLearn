package com.eLearn.eLearning.app.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpResponse<Integer,T> {
    private Integer statusCode;
    private T message;
}
