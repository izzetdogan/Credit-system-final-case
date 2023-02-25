package com.patika.creditsystem.service;

import com.patika.creditsystem.component.CreditScore;
import com.patika.creditsystem.dto.UserDto;
import com.patika.creditsystem.model.User;
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
public class CreditScoreServiceTest {

    @InjectMocks
    CreditScoreService creditScoreService;

    @Mock
    UserService userService;

    @Mock
    CreditScore creditScore;

    @Test
    void addCreditScoreToUserTest(){

        User user = createUserForTest();
        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);
        Mockito.when(creditScore.getRandomCreditScore()).thenReturn(550);

        UserDto userDto = creditScoreService.addCreditScoreToUser(user.getId());

        user.setCreditScore(550);

        Assertions.assertEquals(userDto,UserDto.convert(user));
    }


    private User createUserForTest(){
        LocalDate l = LocalDate.of(2020, Month.APRIL,2);
        return new User("id",123L,"azat","dogan",100L,200L,"+905070330085",l);
    }
}
