package com.patika.creditsystem.controller;


import com.patika.creditsystem.dto.UserDto;
import com.patika.creditsystem.request.CreateUserRequest;
import com.patika.creditsystem.request.UpdateUserRequest;
import com.patika.creditsystem.service.SaveCreditWithUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/app")
public class CreditApplicationController {

    private final SaveCreditWithUserService userService;

    private final Logger LOGGER = LoggerFactory.getLogger(CreditApplicationController.class);

    public CreditApplicationController(SaveCreditWithUserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Create credit with user",
            description = "it takes request from user gives information about credit application result")
    @ApiResponse(responseCode = "400", description = "Invalid supply", content = @Content)
    @PostMapping
    public ResponseEntity<UserDto> addCreditWithUser(@Valid @RequestBody CreateUserRequest request){
        LOGGER.info("Controller: request to create user with credit ");
        return new ResponseEntity<>(userService.addCreditToUser(request), HttpStatus.CREATED);
    }


    @Operation(summary = "Update credit with user",
            description = "it takes request from  user for updating gives information about credit application result")
    @ApiResponse(responseCode = "400", description = "Invalid supply", content = @Content)
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateCreditWithUser(@PathVariable("id") String id,
                                                        @Valid @RequestBody UpdateUserRequest request)
    {
        LOGGER.info("Controller: request to update user with credit");
        return new ResponseEntity<>(userService.updateCreditWithUser(id,request), HttpStatus.OK);

    }
}
