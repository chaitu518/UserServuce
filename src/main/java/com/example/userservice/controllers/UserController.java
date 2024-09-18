package com.example.userservice.controllers;

import com.example.userservice.dtos.*;
import com.example.userservice.exceptions.InvalidPasswordException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }
    @PostMapping("/signup")
    private ResponseEntity<UserDto> signUp(@RequestBody UserRequestDto requestUserDto) throws JsonProcessingException {
        User user = userService.signUp(requestUserDto.getName(),requestUserDto.getEmail(),requestUserDto.getPassword());
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }
    @PostMapping("/login")
    private ResponseEntity<LoginResponseToken> login(@RequestBody LoginRequestDto requestUserDto) throws InvalidPasswordException {
        ResponseEntity<LoginResponseToken> response;
        try {
            Token token = userService.login(requestUserDto.getEmail(),requestUserDto.getPassword());
            LoginResponseToken loginResponseToken = new LoginResponseToken();
            loginResponseToken.setToken(token);
            response= new ResponseEntity<>(loginResponseToken,HttpStatus.OK);
        }
        catch (Exception e){
            response= new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        return response;
    }
    @PostMapping("/logout")
    private ResponseEntity<Void> signOut(@RequestBody LogoutRequestDto requestUserDto){
        try {
            userService.logout(requestUserDto.getToken());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping("/validate/{token}")
    private UserDto validate(@PathVariable String token){
        ResponseEntity<UserDto> response;
        //token present or not
        try{
            User user = userService.validateToken(token);
            return UserDto.fromUser(user);
        }
        catch (Exception e){
            return null;
        }
    }

}
