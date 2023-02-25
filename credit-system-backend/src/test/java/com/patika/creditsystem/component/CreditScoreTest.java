package com.patika.creditsystem.component;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CreditScoreTest {

    @InjectMocks
    CreditScore creditScore;

    @Test
    void getCreditScoreInRange(){
        int actual = creditScore.getRandomCreditScore();
        boolean control = actual == 350 || actual == 600 || actual == 1000;
        Assertions.assertTrue(control);
    }

}
