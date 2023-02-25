package com.patika.creditsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PhoneNumberNotValidException extends  RuntimeException {
    public PhoneNumberNotValidException(String message) {
        super(message);
    }
}
