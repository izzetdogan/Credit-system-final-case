package com.patika.creditsystem.service;

import com.patika.creditsystem.dto.UserDto;
import com.patika.creditsystem.exception.NationalIdNotFoundException;
import com.patika.creditsystem.exception.ResourceNotFoundException;
import com.patika.creditsystem.exception.UserAlreadyExistException;
import com.patika.creditsystem.model.User;
import com.patika.creditsystem.repository.UserRepository;
import com.patika.creditsystem.request.CreateUserRequest;
import com.patika.creditsystem.request.UpdateUserRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final ValidationService validationService;


    public UserService(UserRepository userRepository,
                       ValidationService validationService
    ) {
        this.userRepository = userRepository;
        this.validationService = validationService;

    }
    public List<UserDto> getAllUser(){
        return userRepository.findAll()
                .stream()
                .map(UserDto::convert)
                .toList();
    }


    public User getUserById(String id){
        LOGGER.info("Service: calling getUserById method");
        return findUserById(id);
    }
    public UserDto getUserByNationalIdAndBirthDate(Long natId, String birthdate){
        LocalDate date= validationService.dateFormatterValidation(birthdate);
        Optional<User> user = userRepository.findByNationalIdAndBirthDate(natId,date);
        if(user.isEmpty()){
            LOGGER.warn("Service: user is not found (date and natID) ");
            throw new ResourceNotFoundException("user not found");
        }
        return UserDto.convert(user.get());
    }

    @Transactional
    public UserDto createUser(CreateUserRequest request){
        checkNationalIdExist(request.getNationalId());
        validationService.phoneNumberValidation(request.getPhoneNumber());
        User user = new User(
                request.getNationalId(),
                request.getFirstname(),
                request.getLastname(),
                request.getMonthlyIncome(),
                request.getDeposit(),
                request.getPhoneNumber(),
                validationService.dateFormatterValidation(request.getBirthdate())
        );
        User save = userRepository.save(user);
        LOGGER.info("Service: saving user id  {} to db", user.getId());
        return UserDto.convert(save);
    }
    @Transactional
    public UserDto updateUserById(String id, UpdateUserRequest request){
        validationService.phoneNumberValidation(request.getPhoneNumber());

        User find = findUserById(id);

        User updateOne = new User(
                find.getId(),
                request.getNationalId(),
                request.getFirstname(),
                request.getLastname(),
                request.getMonthlyIncome(),
                request.getDeposit(),
                request.getPhoneNumber(),
                validationService.dateFormatterValidation(request.getBirthdate()),
                find.getCreditScore(),
                find.getCredit()
        );
        LOGGER.info("Service: updating user id  {} ", updateOne.getId());
        return UserDto.convert(userRepository.save(updateOne));
    }

    public UserDto deleteUserById(String id){
       User user = findUserById(id);
       userRepository.delete(user);
       LOGGER.info("Service: user deleted ");
       return UserDto.convert(user);
    }


    public UserDto getUserByNationalId(Long nationalId){
        User find = userRepository.findUserByNationalId(nationalId)
                .orElseThrow(()-> new NationalIdNotFoundException("id Not Found"));
        return UserDto.convert(find);
    }


    private User findUserById(String id){
        return userRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not found: "+ id));
    }

    private void  checkNationalIdExist(Long natId){
        Optional<User> user = userRepository.findUserByNationalId(natId);
        if(user.isPresent()) {
            LOGGER.warn("user already exist {} ", natId);
            throw new UserAlreadyExistException("User Already Exist");
        }
    }



}
