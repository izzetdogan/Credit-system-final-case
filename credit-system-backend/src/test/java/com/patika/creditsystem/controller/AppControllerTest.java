package com.patika.creditsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.patika.creditsystem.dto.CreditDto;
import com.patika.creditsystem.dto.UserDto;
import com.patika.creditsystem.model.Credit;
import com.patika.creditsystem.model.CreditResult;
import com.patika.creditsystem.model.User;
import com.patika.creditsystem.request.CreateCreditDto;
import com.patika.creditsystem.request.CreateUserRequest;
import com.patika.creditsystem.request.UpdateUserRequest;
import com.patika.creditsystem.service.CreditScoreService;
import com.patika.creditsystem.service.CreditService;
import com.patika.creditsystem.service.SmsService;
import com.patika.creditsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.Month;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class AppControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CreditService creditService;

    @MockBean
    UserService userService;

    @MockBean
    SmsService smsService;

    @MockBean
    CreditScoreService creditScoreService;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addUserWithCredit() throws Exception{
        CreateUserRequest request = new CreateUserRequest(12L,"test","testLas",1000L,100L,"+905070335080","2020-01-01");
        LocalDate date = LocalDate.of(2020, Month.APRIL,2);
        Credit credit = new Credit("id2", CreditResult.APPROVED,10000L);

        User user =  new User("id",12L,"test","testLas",10000L,100L,"+905070335080",date,500,credit);


        Mockito.when(userService.createUser(request)).thenReturn(UserDto.convert(user));
        Mockito.when(creditScoreService.addCreditScoreToUser(user.getId())).thenReturn(UserDto.convert(user));
        Mockito.when(creditService.createCredit(new CreateCreditDto(user.getId()))).thenReturn(CreditDto.convert(credit));



        //Then
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/app")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }


    @Test
    void testUpdateUserWithCreditMethod() throws  Exception{
        String userID = "id";
        UpdateUserRequest request = new UpdateUserRequest(12L,"test","testLas",1000L,100L,"+905070335080","2020-01-01");
        LocalDate date = LocalDate.of(2020, Month.APRIL,2);
        Credit credit = new Credit("id2",CreditResult.APPROVED,10000L);

        User user =  new User("id",12L,"test","testLas",10000L,100L,"+905070335080",date,500,credit);


        Mockito.when(userService.updateUserById(userID,request)).thenReturn(UserDto.convert(user));
        Mockito.when(creditScoreService.addCreditScoreToUser(user.getId())).thenReturn(UserDto.convert(user));
        Mockito.when(creditService.updateCredit(user.getId())).thenReturn(CreditDto.convert(credit));



        //Then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/app/"+userID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

}
