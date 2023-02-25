package com.patika.creditsystem.component;


import com.patika.creditsystem.dto.CalculateAmountDto;
import com.patika.creditsystem.dto.CreditAmountResultDto;
import com.patika.creditsystem.model.CreditResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreditLimitCalculatorTest {

    @InjectMocks
    CreditLimitCalculator creditLimitCalculator;

    private final static Integer CREDIT_LIMIT_MULTIPLIER = 4;

    @Test
    void ItShouldReturnRejectedCreditResultWhenCreditScoreLessThan500(){
        CalculateAmountDto calculateDto = new CalculateAmountDto(2000L,100L,400);
        var result = new CreditAmountResultDto(CreditResult.REJECTED,0L,400);
        var expected = creditLimitCalculator.calculateAmount(calculateDto);
        Assertions.assertEquals(result.getCreditLimit(),expected.getCreditLimit());
        Assertions.assertEquals(result.getScore(),expected.getScore());
        Assertions.assertEquals(result.getCreditResult(),expected.getCreditResult());
        Assertions.assertEquals(result,expected);

    }

    @Test // CreditLimit must be 10000+ deposit*0.1; and CreditResult = Approved
    void ItShouldReturnApprovedCreditResultWhenCreditScoreLessThan1000AndMonthlyIncomeLessThan5000(){
        CalculateAmountDto calculateDto = new CalculateAmountDto(2000L,200L,500);
        var result = new CreditAmountResultDto(CreditResult.APPROVED,10020L,500);
        var expected = creditLimitCalculator.calculateAmount(calculateDto);
        Assertions.assertEquals(result.getCreditLimit(),expected.getCreditLimit());
        Assertions.assertEquals(result.getScore(),expected.getScore());
        Assertions.assertEquals(result.getCreditResult(),expected.getCreditResult());
        Assertions.assertEquals(result,expected);

    }

    @Test // CreditLimit must be 20000+ deposit*0.2; and CreditResult = Approved
    void ItShouldReturnApprovedCreditResultWhenCreditScoreLessThan1000AndMonthlyIncomeLessThan10000(){
        CalculateAmountDto calculateDto = new CalculateAmountDto(6000L,200L,500);
        var result = new CreditAmountResultDto(CreditResult.APPROVED,20040L,500);
        var expected = creditLimitCalculator.calculateAmount(calculateDto);
        Assertions.assertEquals(result.getCreditLimit(),expected.getCreditLimit());
        Assertions.assertEquals(result.getScore(),expected.getScore());
        Assertions.assertEquals(result.getCreditResult(),expected.getCreditResult());
        Assertions.assertEquals(result,expected);
    }

    @Test // CreditLimit must be monthlyIncome * creditLimitMultiplier/2  + deposit*0.25; and CreditResult = Approved
    void ItShouldReturnApprovedCreditResultWhenCreditScoreLessThan1000AndMonthlyIncomeGreaterThan10000(){
        CalculateAmountDto calculateDto = new CalculateAmountDto(10000L,200L,500);
        Long creditLimit = calculateDto.getMonthlyIncome()*CREDIT_LIMIT_MULTIPLIER/2 + (calculateDto.getDeposit()*25/100);
        var result = new CreditAmountResultDto(CreditResult.APPROVED,creditLimit,500);
        var expected = creditLimitCalculator.calculateAmount(calculateDto);
        Assertions.assertEquals(result.getCreditLimit(),expected.getCreditLimit());
        Assertions.assertEquals(result.getScore(),expected.getScore());
        Assertions.assertEquals(result.getCreditResult(),expected.getCreditResult());
        Assertions.assertEquals(result,expected);
    }

    @Test // CreditLimit must be monthlyIncome * creditLimitMultiplier/2  + deposit*0.25; and CreditResult = Approved
    void ItShouldReturnApprovedCreditResultWhenCreditScoreGreaterThan1000(){
        CalculateAmountDto calculateDto = new CalculateAmountDto(10000L,200L,1000);
        Long creditLimit = calculateDto.getMonthlyIncome()*CREDIT_LIMIT_MULTIPLIER+ (calculateDto.getDeposit()*50/100);
        var result = new CreditAmountResultDto(CreditResult.APPROVED,creditLimit,1000);
        var expected = creditLimitCalculator.calculateAmount(calculateDto);
        Assertions.assertEquals(result.getCreditLimit(),expected.getCreditLimit());
        Assertions.assertEquals(result.getScore(),expected.getScore());
        Assertions.assertEquals(result.getCreditResult(),expected.getCreditResult());
        Assertions.assertEquals(result,expected);
    }





}


