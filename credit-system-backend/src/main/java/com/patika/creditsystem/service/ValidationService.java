package com.patika.creditsystem.service;

import com.patika.creditsystem.exception.DateTimeFormatterException;
import com.patika.creditsystem.exception.PhoneNumberNotValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    private final Logger LOGGER = LoggerFactory.getLogger(ValidationService.class);
    public LocalDate dateFormatterValidation(String date){
        try{
            LOGGER.info("Service: validating date {} ", date);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(date,formatter);
        }catch(Exception exception){
            LOGGER.warn("date  is not valid {} " ,date);
            throw new DateTimeFormatterException("date format is wrong it must be yyyy-MM-DD");
        }

    }

    public Boolean phoneNumberValidation(String phoneNumber){
        LOGGER.info("Service: validating PhoneNumber {}", phoneNumber);
        if(!useRegex(phoneNumber)){
            LOGGER.warn("number is not valid {} " ,phoneNumber);
            throw  new PhoneNumberNotValidException("Number is not valid. it must be like this : +905073005800");
        }
        return true;
    }

    private  boolean useRegex( String phoneNumber) {
        final Pattern pattern = Pattern.compile("\\+\\d{12}$", Pattern.CASE_INSENSITIVE);
        final Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
