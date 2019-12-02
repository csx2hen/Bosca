package com.bosca.user.controllers;

import com.bosca.user.data.MetadataService;
import com.bosca.user.models.CreateFolderResponse;
import com.bosca.user.models.CreateUserRequest;
import com.bosca.user.models.CreateUserResponse;
import com.bosca.user.models.UserResponse;
import com.bosca.user.services.UserService;
import com.bosca.user.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private Environment environment;
    private ModelMapper modelMapper;

    {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Autowired
    public UserController(UserService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
    }


    // just for test
    @GetMapping
    public String getUsers() {
        return environment.getProperty("token.secret");
    }


    @PostMapping
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest userDetails) {
        // register user in user service
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        // compose return value containing rootDir fileId
        CreateUserResponse returnValue = modelMapper.map(createdUser, CreateUserResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(returnValue);
    }


    @GetMapping(value="/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserDetailsByUserId(userId);
        UserResponse returnValue = modelMapper.map(userDto, UserResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
