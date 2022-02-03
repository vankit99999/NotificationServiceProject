package com.meesho.notificationservice.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    private final ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException,
//                                                       WebRequest webRequest) {
//        StringBuilder stringBuilder = new StringBuilder();
//        List<FieldError> fieldErrorList = methodArgumentNotValidException.getBindingResult().getFieldErrors();
//        for(FieldError fieldError : fieldErrorList) {
//            stringBuilder.append(fieldError.getDefaultMessage());
//            stringBuilder.append(";");
//        }
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                new Date(),
//                stringBuilder.toString(),
//                webRequest.getDescription(false));
//        return errorResponse;
//    }
//
//    @ExceptionHandler(NoSuchElementException.class)
//    @ResponseStatus(value = HttpStatus.NOT_FOUND)
//    private final ErrorResponse noSuchElementException(NoSuchElementException noSuchElementException,
//                                                         WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.NOT_FOUND.value(),
//                new Date(),
//                noSuchElementException.getMessage(),
//                webRequest.getDescription(false));
//        return errorResponse;
//    }
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    private final ErrorResponse illegalArgumentException(IllegalArgumentException illegalArgumentException,
//                                                      WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.BAD_REQUEST.value(),
//                new Date(),
//                illegalArgumentException.getMessage(),
//                webRequest.getDescription(false));
//        return errorResponse;
//    }
//
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorResponse globalException(Exception exception, WebRequest webRequest) {
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.INTERNAL_SERVER_ERROR.value(),
//                new Date(),
//                exception.getMessage(),
//                webRequest.getDescription(false));
//        return errorResponse;
//    }

    //ConstraintViolationException
    //NoSuchElementException
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception exception,WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setDescription(webRequest.getDescription(false));
        errorResponse.setExceptionType(exception.getClass().getCanonicalName());
        errorResponse.setMessage(exception.getLocalizedMessage());
        if(exception instanceof MethodArgumentNotValidException) {
            StringBuilder sb = new StringBuilder();
            for (FieldError fieldError : ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors()) {
                sb.append(fieldError.getDefaultMessage());
                sb.append(";");
            }
            for (ObjectError objectError : ((MethodArgumentNotValidException) exception).getBindingResult().getGlobalErrors()) {
                sb.append(objectError.getDefaultMessage());
                sb.append(";");
            }
            errorResponse.setMessage(sb.toString());
            errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        else if(exception instanceof NoSuchElementException) {
            errorResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        else if(exception instanceof ConstraintViolationException
                || exception instanceof MethodArgumentTypeMismatchException
                || exception instanceof IllegalArgumentException) {
            errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        else
        {
            errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
