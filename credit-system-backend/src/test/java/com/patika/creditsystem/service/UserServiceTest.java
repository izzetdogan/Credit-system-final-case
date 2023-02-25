package com.patika.creditsystem.service;

import com.patika.creditsystem.dto.UserDto;
import com.patika.creditsystem.exception.NationalIdNotFoundException;
import com.patika.creditsystem.exception.ResourceNotFoundException;
import com.patika.creditsystem.model.User;
import com.patika.creditsystem.repository.UserRepository;
import com.patika.creditsystem.request.CreateUserRequest;
import com.patika.creditsystem.request.UpdateUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private ValidationService validationService;

    @Mock
    private UserRepository userRepository;


    @Test
    void testGetAllUser_ItShouldReturnUsers(){
        userService.getAllUser();
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void testCreateUser(){
        LocalDate date= testDateFormatToLocalDate("2020-01-01");

        User user = new User(123L,"test","test",100L,10L,"507",date);
        CreateUserRequest request= new CreateUserRequest(
                user.getNationalId(),
                user.getFirstname(),
                user.getLastname(),
                user.getMonthlyIncome(),
                user.getDeposit(),
                user.getPhoneNumber(),
                "2020-01-01");
        Mockito.when(validationService.dateFormatterValidation("2020-01-01")).thenReturn(date);
        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDto actual = userService.createUser(request);

        Assertions.assertEquals(actual.getNationalId(),UserDto.convert(user).getNationalId());
        Assertions.assertEquals(actual.getBirthdate(),user.getBirthdate());

    }

    @Test
    void testUpdateUser(){
        LocalDate date= testDateFormatToLocalDate("2020-01-01");
        User user = new User("id",123L,"updatename","updateLst",100L,10L,"507",date);


        UpdateUserRequest request = new UpdateUserRequest(user.getNationalId(),
                "updatename","updateLst",user.getMonthlyIncome(),user.getDeposit(),user.getPhoneNumber(),"2020-01-01");

        Mockito.when(validationService.dateFormatterValidation("2020-01-01")).thenReturn(date);

        Mockito.when(userRepository.save(user)).thenReturn(user);

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserDto actual = userService.updateUserById("id",request);

        Assertions.assertEquals(actual,UserDto.convert(user));
    }

    // Get One User BY ID testing
    @Test
    void testGetOneUserById_ItShouldGetOneUserByIdWhenIdIsFound(){
        User user=this.createUserForTest();
        Optional<User> optionalUser=  Optional.of(user);

        Mockito.when(userRepository.findById(optionalUser.get().getId())).thenReturn(optionalUser);

        User userFind = userService.getUserById(user.getId());
        Assertions.assertEquals(userFind,optionalUser.get());
    }

    @Test
    void  testGetOneUserById_ItShouldThrowResourceNotFoundExceptionWhenUSerIdNotFound(){
        String userId ="123";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->userService.getUserById(userId));
        Mockito.verify(userRepository).findById(userId);

    }

    // Get User By National ID Test - Case
    @Test
    void testGetUserByNationalId_ITShouldReturnUserWhenNationalIdFound(){
        User user = createUserForTest();
        Mockito.when(userRepository.findUserByNationalId(user.getNationalId())).thenReturn(Optional.of(user));

        UserDto userDto= userService.getUserByNationalId(user.getNationalId());

        Assertions.assertEquals(userDto,UserDto.convert(user));

        Mockito.verify(userRepository).findUserByNationalId(user.getNationalId());
    }

    @Test
    void testGetUserByNationalId_ItShouldThrowResourceNotFoundExceptionWhenNationalIdNotFound(){
        User user = createUserForTest();
        Mockito.when(userRepository.findUserByNationalId(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NationalIdNotFoundException.class,()->
                userService.getUserByNationalId(user.getNationalId()));
    }

    // Find USerBy National ID and BirthDate Test-Case
    @Test
    void testFindUserByIdAndBirthDate_ITShouldFindUserWhenNationalIDAndBirthDateFound(){
        User user = createUserForTest();

        Mockito.when(userRepository.findByNationalIdAndBirthDate(user.getNationalId(),user.getBirthdate())).thenReturn(Optional.of(user));
        Mockito.when(validationService.dateFormatterValidation(testDateFormat(user.getBirthdate()))).thenReturn(user.getBirthdate());

        UserDto userDto= userService.getUserByNationalIdAndBirthDate(user.getNationalId(),testDateFormat(user.getBirthdate()));

        Assertions.assertEquals(userDto.getBirthdate(),UserDto.convert(user).getBirthdate());

        Mockito.verify(userRepository).findByNationalIdAndBirthDate(user.getNationalId(),user.getBirthdate());
    }

    @Test
    void testFindUserByIdAndBirthDate_ItShouldThrowResourceNotFoundExceptionWhenBirthDateISNotFound(){
        User user = createUserForTest();
        Mockito.when(userRepository.findByNationalIdAndBirthDate(user.getNationalId(),user.getBirthdate())).thenReturn(Optional.empty());
        Mockito.when(validationService.dateFormatterValidation(testDateFormat(user.getBirthdate()))).thenReturn(user.getBirthdate());


        Assertions.assertThrows(ResourceNotFoundException.class,
                ()->userService.getUserByNationalIdAndBirthDate(user.getNationalId(),testDateFormat(user.getBirthdate())));

    }

    // Delete Test Case
    @Test
    void testDeleteUserById_itShouldDeleteUserWhenUserIdFound(){
        User user = createUserForTest();
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUserById(user.getId());

        Mockito.verify(userRepository).findById(user.getId());
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUserById_itShouldThrowResourceNotFoundExceptionWhenUserIdNotFound(){
        String userId= "123";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, ()->
                userService.deleteUserById(userId));
        Mockito.verify(userRepository).findById(userId);

    }



    private User createUserForTest(){

        LocalDate a= testDateFormatToLocalDate("2020-01-01");
        return new User("id",123L,"azat","dogan",100L,200L,"+905070330085",a);
    }

    private LocalDate testDateFormatToLocalDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date,formatter);
    }
    private String testDateFormat(LocalDate localDate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return localDate.format(formatter);

    }
         /*
    @Test
    @Disabled
    void getAllUser(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse("2000-01-01",formatter);
        User user = new User(123456L,"azat","dogan",10000L,500L,"555",localDate,null);
        User user2 = new User(1234567L,"azat","dogan",10000L,500L,"555",localDate,null);
        List<User> expectedList = Arrays.asList(user,user2);


        when(userRepository.findAll()).thenReturn(expectedList);

        List<UserDto> userDtos = userService.getAllUser();
        Assertions.assertEquals(userDtos.size(), expectedList.size());
        Assertions.assertEquals(userDtos.get(0), UserDto.convert(expectedList.get(0)));
    }

    @Test
    @Disabled
    void getOneUserById(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse("2000-01-01",formatter);
        User user = new User("1ud",123456L,"azat","dogan",10000L,500L,"555",localDate,null);
        Optional<User>  optionalUser=  Optional.of(user);

        when(userRepository.findById("1ud")).thenReturn(optionalUser);

        UserDto userFind = userService.getOneUserById(user.getId());
        Assertions.assertEquals(userFind,UserDto.convert(optionalUser.get()));

    }


        }
     */
}
