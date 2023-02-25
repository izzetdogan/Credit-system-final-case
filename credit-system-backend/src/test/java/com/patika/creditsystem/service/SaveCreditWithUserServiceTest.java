package com.patika.creditsystem.service;


import com.patika.creditsystem.dto.CreditDto;
import com.patika.creditsystem.dto.SendSmsDto;
import com.patika.creditsystem.dto.UserDto;
import com.patika.creditsystem.model.Credit;
import com.patika.creditsystem.model.CreditResult;
import com.patika.creditsystem.model.User;
import com.patika.creditsystem.request.CreateCreditDto;
import com.patika.creditsystem.request.CreateUserRequest;
import com.patika.creditsystem.request.UpdateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

@ExtendWith(MockitoExtension.class)
public class SaveCreditWithUserServiceTest {

    @InjectMocks
    SaveCreditWithUserService underTestService;

    @Mock
    UserService userService;

    @Mock
    CreditService creditService;

    @Mock
    CreditScoreService creditScoreService;


    @Mock
    SmsService smsService;

    @Test
    void testCreateUserWithCreditMethod(){
        CreateUserRequest request = new CreateUserRequest(12L,"test","testLas",1000L,100L,"+905070335080","2020-01-01");
        LocalDate date = LocalDate.of(2020, Month.APRIL,2);
        Credit credit = new Credit("id2",CreditResult.APPROVED,10000L);

        User user =  new User("id",12L,"test","testLas",10000L,100L,"+905070335080",date,500,credit);


        Mockito.when(userService.createUser(request)).thenReturn(UserDto.convert(user));
        Mockito.when(creditScoreService.addCreditScoreToUser(user.getId())).thenReturn(UserDto.convert(user));
        Mockito.when(creditService.createCredit(new CreateCreditDto(user.getId()))).thenReturn(CreditDto.convert(credit));


        UserDto actual = underTestService.addCreditToUser(request);

        Assertions.assertEquals(user.getCreditScore(),actual.getCreditScore());

        Assertions.assertEquals(user.getCredit(), actual.getCredit());

        Assertions.assertEquals(UserDto.convert(user), actual);

        Mockito.verify(smsService, Mockito.times(1)).sendSms(new SendSmsDto(
                "Credit request saved",
                request.getNationalId(),
                request.getPhoneNumber(),
                credit.getCreditLimit(),
                credit.getCreditResult()
        ));

    }



    @Test
    void testUpdateUserWithCreditMethod(){
        String userID = "id";
        UpdateUserRequest request = new UpdateUserRequest(12L,"test","testLas",1000L,100L,"+905070335080","2020-01-01");
        LocalDate date = LocalDate.of(2020, Month.APRIL,2);
        Credit credit = new Credit("id2",CreditResult.APPROVED,10000L);

        User user =  new User("id",12L,"test","testLas",10000L,100L,"+905070335080",date,500,credit);


        Mockito.when(userService.updateUserById(userID,request)).thenReturn(UserDto.convert(user));
        Mockito.when(creditScoreService.addCreditScoreToUser(user.getId())).thenReturn(UserDto.convert(user));
        Mockito.when(creditService.updateCredit(user.getId())).thenReturn(CreditDto.convert(credit));


        UserDto actual = underTestService.updateCreditWithUser(userID,request);

        Assertions.assertEquals(user.getCreditScore(),actual.getCreditScore());

        Assertions.assertEquals(user.getCredit(), actual.getCredit());

        Assertions.assertEquals(UserDto.convert(user), actual);

        Mockito.verify(smsService, Mockito.times(1)).sendSms(new SendSmsDto(
                "CreditRequest updated",
                request.getNationalId(),
                request.getPhoneNumber(),
                credit.getCreditLimit(),
                credit.getCreditResult()
        ));

    }



}
