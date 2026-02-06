package com.pismo.test.cards.exception.handler;

import com.pismo.test.cards.exception.AppBusinessException;
import com.pismo.test.cards.exception.AppRuntimeException;
import com.pismo.test.cards.exception.ErrorResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({AppBusinessException.class, AppRuntimeException.class})
    public ResponseEntity<ErrorResponse> handleBusinessException(AppBusinessException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setError("Internal Server Error");
        response.setMessage(ex.getMessage());
        response.setStatus(ex.getStatus());
        response.setTimestamp(LocalDateTime.now());
        response.setId(ex.getId());
        return ResponseEntity.status(Math.toIntExact(ex.getStatus())).body(response);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class, TypeMismatchException.class})
    public ResponseEntity<ErrorResponse> handleTypeMismatch(Exception ex) {
        ErrorResponse response = new ErrorResponse();
        response.setError("Bad Request");
        response.setMessage(ex.getMessage());
        response.setStatus(400);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(Math.toIntExact(400)).body(response);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoResourceFoundException ex) {
        ErrorResponse response = new ErrorResponse();
        response.setError("Not Found Request");
        response.setMessage(ex.getMessage());
        response.setStatus(404);
        response.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(Math.toIntExact(404)).body(response);
    }
}
