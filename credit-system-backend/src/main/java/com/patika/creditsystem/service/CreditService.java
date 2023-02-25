package com.patika.creditsystem.service;

import com.patika.creditsystem.component.CreditLimitCalculator;
import com.patika.creditsystem.dto.CalculateAmountDto;
import com.patika.creditsystem.dto.CreditAmountResultDto;
import com.patika.creditsystem.dto.CreditDto;
import com.patika.creditsystem.exception.ResourceNotFoundException;
import com.patika.creditsystem.model.Credit;
import com.patika.creditsystem.model.User;
import com.patika.creditsystem.repository.CreditRepository;
import com.patika.creditsystem.request.CreateCreditDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditService {
    private final CreditRepository creditRepository;
    private final CreditLimitCalculator creditLimitCalculator;

    private final UserService userService;
    private final static Logger LOGGER = LoggerFactory.getLogger(CreditService.class);

    public CreditService(CreditRepository creditRepository,
                         CreditLimitCalculator creditLimitCalculator,
                         UserService userService) {
        this.creditRepository = creditRepository;
        this.creditLimitCalculator = creditLimitCalculator;
        this.userService = userService;
    }
    public List<CreditDto> getAllCredit(){
        return creditRepository.findAll()
                .stream()
                .map(CreditDto::convert)
                .toList();
    }

    public CreditDto getOneCreditById(String id){
        return CreditDto.convert(findCreditById(id));
    }

    public CreditDto createCredit(CreateCreditDto req){
        User user = userService.getUserById(req.getUserId());
        CreditAmountResultDto creditAmountResult = getCreditAmountResult(user);
        Credit credit = new Credit(
                creditAmountResult.getCreditResult(),
                creditAmountResult.getCreditLimit(),
                user);
        Credit save = creditRepository.save(credit);
        LOGGER.info("Service: Saving credit {} to db " ,save.getId());
        return CreditDto.convert(save);
    }

    public CreditDto updateCredit(String id){

        User user = userService.getUserById(id);


        CreditAmountResultDto creditAmountResult = getCreditAmountResult(user);
        Credit credit = new Credit(
                user.getCredit().getId(),
                creditAmountResult.getCreditResult(),
                creditAmountResult.getCreditLimit(),
                user);
        Credit save = creditRepository.save(credit);
        LOGGER.info("Service: Updating credit {} " ,credit.getId());

        return CreditDto.convert(save);
    }

    public CreditDto deleteCreditById(String id){
        Credit find = findCreditById(id);
        creditRepository.delete(find);
        LOGGER.info("Service: credit deleted {}", find.getId());
        return CreditDto.convert(find);
    }

    private Credit findCreditById(String id){
        return creditRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Credit not found id " + id  ));


    }
    private CreditAmountResultDto getCreditAmountResult(User user){
        CalculateAmountDto calculateAmountDto = new CalculateAmountDto(
                user.getMonthlyIncome(),
                user.getDeposit(),
                user.getCreditScore()
        );
        return creditLimitCalculator.calculateAmount(calculateAmountDto);

    }


}
