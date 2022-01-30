package com.meesho.notificationservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private final ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException,
                                                       WebRequest webRequest) {
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldError> fieldErrorList = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        for(FieldError fieldError : fieldErrorList) {
            stringBuilder.append(fieldError.getDefaultMessage());
            stringBuilder.append(";");
        }
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                stringBuilder.toString(),
                webRequest.getDescription(false));
        return errorResponse;
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    private final ErrorResponse noSuchElementException(NoSuchElementException noSuchElementException,
                                                         WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                noSuchElementException.getMessage(),
                webRequest.getDescription(false));
        return errorResponse;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    private final ErrorResponse illegalArgumentException(IllegalArgumentException illegalArgumentException,
                                                      WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                illegalArgumentException.getMessage(),
                webRequest.getDescription(false));
        return errorResponse;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse globalException(Exception exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return errorResponse;
    }
}
