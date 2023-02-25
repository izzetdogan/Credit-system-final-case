package com.patika.creditsystem.service;

import com.patika.creditsystem.component.CreditScore;
import com.patika.creditsystem.dto.UserDto;
import com.patika.creditsystem.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreditScoreService {

    private final UserService userService;
    private final CreditScore creditScore;
    private final Logger LOGGER = LoggerFactory.getLogger(CreditScoreService.class);


    public CreditScoreService(UserService userService, CreditScore creditScore) {
        this.userService = userService;
        this.creditScore = creditScore;
    }

    public UserDto addCreditScoreToUser(String id){
        User user = userService.getUserById(id);
        user.setCreditScore(creditScore.getRandomCreditScore());
        LOGGER.info("Service: setting creditScore: {}  to User: {}  ", user.getCreditScore(),user.getId());
        return UserDto.convert(user);
    }
}
