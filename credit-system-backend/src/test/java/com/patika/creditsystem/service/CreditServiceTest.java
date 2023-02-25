package com.patika.creditsystem.service;

import com.patika.creditsystem.component.CreditLimitCalculator;
import com.patika.creditsystem.dto.CalculateAmountDto;
import com.patika.creditsystem.dto.CreditAmountResultDto;
import com.patika.creditsystem.dto.CreditDto;
import com.patika.creditsystem.exception.ResourceNotFoundException;
import com.patika.creditsystem.model.Credit;
import com.patika.creditsystem.model.CreditResult;
import com.patika.creditsystem.model.User;
import com.patika.creditsystem.repository.CreditRepository;
import com.patika.creditsystem.request.CreateCreditDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CreditServiceTest {

    @InjectMocks
    CreditService creditService;

    @Mock
    CreditRepository creditRepository;

    @Mock
    CreditLimitCalculator creditLimitCalculator;

    @Mock
    UserService userService;

    @Test
    void getAllCreditTest(){
        Credit credit = new Credit(CreditResult.APPROVED,1000L,getSomeUser().get(0));
        Credit credit1 = new Credit(CreditResult.REJECTED,100L,getSomeUser().get(1));
        Credit credit2 = new Credit(CreditResult.REJECTED,500L,getSomeUser().get(2));

        List<Credit> allCredit = Arrays.asList(credit1,credit2,credit);

        Mockito.when(creditRepository.findAll()).thenReturn(allCredit);

        List<CreditDto> creditDtos = creditService.getAllCredit();

        Assertions.assertEquals(creditDtos.size(),allCredit.size());
        Assertions.assertEquals(creditDtos.get(2),CreditDto.convert(allCredit.get(2)));

    }

    @Test
    void testCreateCredit(){
        User user = getSomeUser().get(1);

        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);

        Credit credit = new Credit(CreditResult.APPROVED,1000L,user);


        Mockito.when(creditRepository.save(credit)).thenReturn(credit);
        Mockito.when(creditLimitCalculator.calculateAmount(new CalculateAmountDto(user.getMonthlyIncome(),user.getDeposit(),user.getCreditScore()))).thenReturn(new CreditAmountResultDto(CreditResult.APPROVED,1000L,
        user.getCreditScore()));
        CreditDto actual = creditService.createCredit(new CreateCreditDto(user.getId()));

        Assertions.assertEquals(actual,CreditDto.convert(credit));

    }


    @Test
    void testUpdateCredit(){
        User user = getSomeUser().get(1);

        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);

        Credit credit = new Credit(CreditResult.APPROVED,1000L,user);


        Mockito.when(creditRepository.save(credit)).thenReturn(credit);
        Mockito.when(creditLimitCalculator.calculateAmount(new CalculateAmountDto(user.getMonthlyIncome(),user.getDeposit(),user.getCreditScore()))).thenReturn(new CreditAmountResultDto(CreditResult.APPROVED,1000L,
                user.getCreditScore()));
        CreditDto actual = creditService.createCredit(new CreateCreditDto(user.getId()));

        Assertions.assertEquals(actual,CreditDto.convert(credit));

    }



    @Test
    void testGetOneCreditByID_ItShouldFindCredit(){
        Credit credit = new Credit("id",CreditResult.APPROVED,1000L,getSomeUser().get(0));

        Mockito.when(creditRepository.findById(credit.getId())).thenReturn(Optional.of(credit));

        CreditDto creditDto = creditService.getOneCreditById(credit.getId());

        Assertions.assertEquals(creditDto,CreditDto.convert(credit));

    }

    @Test
    void testGetOneCreditByID_ItShouldThrowErrorWhenNotFoundCredit(){
        String creditId ="233";
        Mockito.when(creditRepository.findById(creditId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->creditService.getOneCreditById(creditId));

        Mockito.verify(creditRepository).findById(creditId);

    }


    // Delete Test Case
    @Test
    void testDeleteCreditById_itShouldDeleteUserWhenUserIdFound(){
        Credit credit = new Credit("id",CreditResult.APPROVED,1000L,getSomeUser().get(0));

        Mockito.when(creditRepository.findById(credit.getId())).thenReturn(Optional.of(credit));

        creditService.deleteCreditById(credit.getId());

        Mockito.verify(creditRepository).findById(credit.getId());
        Mockito.verify(creditRepository).delete(credit);
    }

    @Test
    void testDeleteUserById_itShouldThrowResourceNotFoundExceptionWhenUserIdNotFound(){
        String creditId= "123";
        Mockito.when(creditRepository.findById(creditId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, ()->
                creditService.deleteCreditById(creditId));
        Mockito.verify(creditRepository).findById(creditId);

    }





    private List<User> getSomeUser(){
        LocalDate date = LocalDate.of(2020, Month.APRIL,2);
        User user = new User("id",12345L,"test","dogan",1200L,100L,"5057",date);
        User user2 = new User("id2",123456L,"test2","dogan",1200L,100L,"5057",date);
        User user3 = new User("id3",1234567L,"test3","dogan",1200L,100L,"5057",date);

        return Arrays.asList(user,user2,user3);
    }


}
