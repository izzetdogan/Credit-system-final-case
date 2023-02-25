package com.patika.creditsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUserNotFoundException(ResourceNotFoundException exception){
        String message = exception.getMessage();
        ApiErrorResponse response = new ApiErrorResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NationalIdNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleNationalIdNotFoundException(NationalIdNotFoundException exception){
        String message = exception.getMessage();
        ApiErrorResponse response = new ApiErrorResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiErrorResponse> handleUserAlreadyExistException(UserAlreadyExistException exception){
        String message = exception.getMessage();
        ApiErrorResponse response = new ApiErrorResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PhoneNumberNotValidException.class)
    public ResponseEntity<ApiErrorResponse> phoneNumberNotValidException(PhoneNumberNotValidException exception){
        String message = exception.getMessage();
        ApiErrorResponse response = new ApiErrorResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeFormatterException.class)
    public ResponseEntity<ApiErrorResponse> dateFormatterExceptionHandler(DateTimeFormatterException exception){
        String message = exception.getMessage();
        ApiErrorResponse response = new ApiErrorResponse(message,false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }



}
