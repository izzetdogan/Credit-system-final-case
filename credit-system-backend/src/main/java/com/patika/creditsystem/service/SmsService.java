package com.patika.creditsystem.service;

import com.patika.creditsystem.config.TwilioInitializer;
import com.patika.creditsystem.dto.SendSmsDto;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    @Value("${twilio.phone.number}")
    private String phoneNumber;
    private final ValidationService validationService;
    private final static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);

    public SmsService(ValidationService validationService) {
        this.validationService = validationService;
    }


    public void sendSms(SendSmsDto sendSmsDto){

        validationService.phoneNumberValidation(sendSmsDto.getPhoneNumber());
        String messageBuild = sendSmsDto.getMessage()+
                " National id: " + sendSmsDto.getNationalId() +
                "  Limit : " + sendSmsDto.getCreditLimit() +
                " Result " + sendSmsDto.getCreditResult();


        Message.creator(new PhoneNumber(sendSmsDto.getPhoneNumber()),
                new PhoneNumber(phoneNumber),
                messageBuild
        ).create();

        LOGGER.info("SmsService: Send sms to  {}", sendSmsDto.getPhoneNumber());
    }


}
