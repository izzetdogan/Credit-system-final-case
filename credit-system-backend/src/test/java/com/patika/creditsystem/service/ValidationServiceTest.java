package com.patika.creditsystem.service;

import com.patika.creditsystem.exception.DateTimeFormatterException;
import com.patika.creditsystem.exception.PhoneNumberNotValidException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceTest {

    @InjectMocks
    ValidationService validationService;

    @Test
    void whenDateInputIsNotValidITShouldThrowDateFormatterException(){
        String date= "2002/10/10";
        Assertions.assertThrows(DateTimeFormatterException.class,
                ()-> validationService.dateFormatterValidation(date));
    }

    @Test
    void whenDateInputIsValidItShouldReturnLocalDate(){
        String date= "2002-10-10";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate input = LocalDate.parse(date,formatter);
        Assertions.assertEquals(validationService.dateFormatterValidation(date),input);
    }


    @Test
    void whenPhoneNumberIsNotValidItShouldThrowPhoneNumberNotValidException(){
        String phoneNumber="+5070330085";
        //StringPhoneNumber ="+905070330085";
        Assertions.assertThrows(PhoneNumberNotValidException.class,
                ()->validationService.phoneNumberValidation(phoneNumber));
    }

    @Test
    void whenPhoneNumberIsValidItShouldReturnTrue(){
        String phoneNumber="+905070330085";
        Assertions.assertEquals(Boolean.TRUE,validationService.phoneNumberValidation(phoneNumber));
    }




}
