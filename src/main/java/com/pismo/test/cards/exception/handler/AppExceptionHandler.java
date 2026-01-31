package com.pismo.test.cards.exception.handler;

import com.pismo.test.cards.exception.AppBusinessException;
import com.pismo.test.cards.exception.AppRuntimeException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler({AppBusinessException.class, AppRuntimeException.class})
    public ResponseEntity<Map<String, Object>> handleBusinessException(AppBusinessException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus());
        body.put("id", ex.getId());
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler({UnsupportedMediaTypeStatusException.class, TypeMismatchException.class})
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(TypeMismatchException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 400);
        body.put("error", "Bad Request");
        body.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(UnsupportedMediaTypeStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 404);
        body.put("error", "Not Found");
        body.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

}

