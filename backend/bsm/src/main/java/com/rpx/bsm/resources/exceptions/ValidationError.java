package com.rpx.bsm.resources.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@Getter
public class ValidationError extends StandardError {

    private List<FieldMessage> fieldErrors = new ArrayList<>();

    public ValidationError(Instant timestamp, Integer status, String error, String message, String path) {
        super( timestamp,  status,  error,  message,  path);
    }

    public void addError(String fieldName, String fieldMessage) {
        fieldErrors.add(new FieldMessage(fieldName, fieldMessage));
    }


}
