package com.patika.creditsystem.controller;

import com.patika.creditsystem.dto.UserDto;
import com.patika.creditsystem.request.CreateUserRequest;
import com.patika.creditsystem.request.UpdateUserRequest;
import com.patika.creditsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    @Operation(summary = "List all user ")
    public ResponseEntity<List<UserDto>> getAllUser(){
        LOGGER.info("Controller: request to list  all data");
        return ResponseEntity.ok(userService.getAllUser());
    }


    @Operation(summary = "Get a User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User By id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supply", content = @Content),
            @ApiResponse(responseCode = "404", description = "User  not found by id", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") String id){
        LOGGER.info("Controller: request to  fetch a data with id");
        return ResponseEntity.ok(UserDto.convert(userService.getUserById(id)));
    }


    @Operation(summary = "Get a User by national id and birthdate ",
    description = "it takes national id and birthdate as a query parameter and the returns the result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the User By id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid supply", content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "User not found by id || birthdate is null or birthdate format is wrong yyyy-mm-dd",
                    content = @Content)
    })
    @GetMapping("/search")
    public ResponseEntity<UserDto> getDateUser(@RequestParam(value = "natId" ,required = false ) Long natId,
                                               @RequestParam("date") String date){
        LOGGER.info("Controller: request to  fetch data with national-id and  date param");
        return ResponseEntity.ok(userService.getUserByNationalIdAndBirthDate(natId,date));
    }


    @GetMapping("/national-id/{natId}")
    public ResponseEntity<UserDto> getUserByNationalId(@PathVariable("natId") Long natId){
        LOGGER.info("Controller: request to  fetch a  data with national-id ");
        return ResponseEntity.ok(userService.getUserByNationalId(natId));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid CreateUserRequest request){
        LOGGER.info("Controller: request to  create user without credit");
        return new ResponseEntity<>(userService.createUser(request), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") String id,
                                              @RequestBody @Valid UpdateUserRequest request
    ){
        LOGGER.info("Controller: request to  update user without credit");
        return new ResponseEntity<>(userService.updateUserById(id,request),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete users by id",description = "this endpoints delete user by id")
    public ResponseEntity<UserDto> deleteUserById(@PathVariable("id") String id){
        LOGGER.info("Controller: request to  delete user");
        return  ResponseEntity.ok(userService.deleteUserById(id));
    }


}
