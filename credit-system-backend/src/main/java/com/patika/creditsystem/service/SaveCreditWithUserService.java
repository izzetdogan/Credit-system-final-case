package com.patika.creditsystem.service;

import com.patika.creditsystem.dto.CreditDto;
import com.patika.creditsystem.dto.SendSmsDto;
import com.patika.creditsystem.dto.UserDto;
import com.patika.creditsystem.request.CreateCreditDto;
import com.patika.creditsystem.request.CreateUserRequest;
import com.patika.creditsystem.request.UpdateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaveCreditWithUserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SaveCreditWithUserService.class);
    private final UserService userService;
    private final CreditService creditService;

    private final  CreditScoreService creditScoreService;
    private final SmsService smsService;

    public SaveCreditWithUserService(UserService userService,
                                     CreditService creditService,
                                     CreditScoreService creditScoreService, SmsService smsService
    ) {
        this.userService = userService;
        this.creditService = creditService;
        this.creditScoreService = creditScoreService;
        this.smsService = smsService;
    }


    @Transactional
    public UserDto addCreditToUser(CreateUserRequest request){

        UserDto userDto = userService.createUser(request);
        UserDto user = creditScoreService.addCreditScoreToUser(userDto.getId());
        CreditDto credit = creditService.createCredit(new CreateCreditDto(user.getId()));
        SendSmsDto sendSmsDto = new SendSmsDto(
                "Credit request saved",
                request.getNationalId(),
                request.getPhoneNumber(),
                credit.getCreditLimit(),
                credit.getCreditResult()
        );
        user.setCredit(CreditDto.convertToCredit(credit));
        smsService.sendSms(sendSmsDto);
        LOGGER.info("Service: request  credit with user is completed");
        return user;

    }


    @Transactional
    public UserDto updateCreditWithUser(String id,UpdateUserRequest request){
        UserDto userDto = userService.updateUserById(id,request);
        UserDto user = creditScoreService.addCreditScoreToUser(userDto.getId());
        CreditDto credit = creditService.updateCredit(userDto.getId());
        SendSmsDto sendSmsDto = new SendSmsDto(
                "CreditRequest updated",
                request.getNationalId(),
                request.getPhoneNumber(),
                credit.getCreditLimit(),
                credit.getCreditResult()
        );
        user.setCredit(CreditDto.convertToCredit(credit));
        smsService.sendSms(sendSmsDto);
        LOGGER.info("Service: request  credit with user is completed");
        return user;

    }


}

/*

    @Transactional
    public UserDto AddCreditToUser(CreateUserRequest createUserRequest){
        try {
            var user = userService.createUser(createUserRequest);
            var credit = creditService.createCredit(new CreateCreditDto(user.getId(), user.getMonthlyIncome(),user.getDeposit(),user.getCreditScore()));
            var sendSmsDto = new SendSmsDto(createUserRequest.getNationalId(), createUserRequest.getPhoneNumber(), credit.getCreditLimit(),credit.getCreditResult());
            user.setCredit(CreditDto.convertToCredit(credit));
            smsService.sendSms(sendSmsDto);
            return user;
        }catch (Exception ex){
            throw new ResourceNotFoundException(ex.getMessage());
        }


    }

 */
