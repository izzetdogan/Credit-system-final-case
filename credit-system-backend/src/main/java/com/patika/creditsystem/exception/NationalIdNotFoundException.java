package com.patika.creditsystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NationalIdNotFoundException extends  RuntimeException{
    public NationalIdNotFoundException(String message) {
        super(message);
    }
}
