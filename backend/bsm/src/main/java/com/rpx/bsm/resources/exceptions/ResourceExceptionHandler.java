package com.rpx.bsm.resources.exceptions;

import com.rpx.bsm.resources.exceptions.DatabaseException;
import com.rpx.bsm.resources.exceptions.ResourceNotFoundException;
import com.rpx.bsm.resources.exceptions.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> dataBase(DatabaseException e, HttpServletRequest request) {
        String error = "Databese error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ValidateInstallments.class)
    public ResponseEntity<StandardError> hasInstallment(ValidateInstallments e) {
        String error = "Parcelas em aberto";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        //String message =  "A despesa não pode ser excluída, pois existe parcelas pendentes";
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), "/expenses");
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        String error = "Validação error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationError validationError = new ValidationError(Instant.now(), status.value(), error, "Validação de error", request.getRequestURI());

        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            validationError.addError(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(validationError);
    }

}
