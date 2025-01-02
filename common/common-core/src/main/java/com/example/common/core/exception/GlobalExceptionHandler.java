package com.example.common.core.exception;

import com.example.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.example.common.core.exception.user.UserException;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public R<String> handleRuntimeException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public R<String> handleUserException(UserException e) {
        log.error(e.getMessage(), e);
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return R.fail(message);
    }
} 