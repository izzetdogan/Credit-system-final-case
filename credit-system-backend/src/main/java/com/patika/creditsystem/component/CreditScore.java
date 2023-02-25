package com.patika.creditsystem.component;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class CreditScore {

    public Integer getRandomCreditScore(){
        List<Integer> list = Arrays.asList(350,600,1000);
        Random rand = new Random();
        int lengthOfList = list.size();
        return list.get(rand.nextInt(lengthOfList));

    }
}
