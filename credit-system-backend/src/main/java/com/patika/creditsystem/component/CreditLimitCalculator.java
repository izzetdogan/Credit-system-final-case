package com.patika.creditsystem.component;

import com.patika.creditsystem.dto.CalculateAmountDto;
import com.patika.creditsystem.dto.CreditAmountResultDto;
import com.patika.creditsystem.model.CreditResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CreditLimitCalculator {
    private final static Integer CREDIT_LIMIT_MULTIPLIER = 4;

    private final Logger LOGGER = LoggerFactory.getLogger(CreditLimitCalculator.class);



    public CreditAmountResultDto calculateAmount(CalculateAmountDto calculateAmountDto){
        int score = calculateAmountDto.getCreditScore()==null ? 0: calculateAmountDto.getCreditScore();


        long monthlyIncome = calculateAmountDto.getMonthlyIncome()==null? 0:calculateAmountDto.getMonthlyIncome();
        CreditResult creditResult;
        long limit =0L;
        long deposit = calculateAmountDto.getDeposit()==null?0:calculateAmountDto.getDeposit();

        if(score<500){
            creditResult = CreditResult.REJECTED;
        }else if(score<1000 && monthlyIncome<5000){
            creditResult=CreditResult.APPROVED;
            limit = 10000 + deposit*10/100;
        }else if(score<1000 && monthlyIncome<10000){
            creditResult=CreditResult.APPROVED;
            limit = 20000 +(deposit*20/100);
        }else if(score<1000){
            creditResult=CreditResult.APPROVED;
            limit = monthlyIncome*CREDIT_LIMIT_MULTIPLIER/2 +(deposit*25/100);
        } else {
            creditResult=CreditResult.APPROVED;
            limit = (monthlyIncome * CREDIT_LIMIT_MULTIPLIER)+ deposit*50/100;
        }

        LOGGER.info("CreditLimitCalculator: return Limit calculator result  to credit service {} {}", creditResult,limit);
        return new CreditAmountResultDto(creditResult,limit,score);


    }
}
