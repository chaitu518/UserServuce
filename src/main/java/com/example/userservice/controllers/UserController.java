package com.example.userservice.controllers;

import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.LogoutRequestDto;
import com.example.userservice.dtos.UserRequestDto;
import com.example.userservice.dtos.UserDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;

    }
    @PostMapping("/signup")
    private ResponseEntity<UserDto> signUp(@RequestBody UserRequestDto requestUserDto){
        User user = userService.signUp(requestUserDto.getName(),requestUserDto.getEmail(),requestUserDto.getPassword());
        return new ResponseEntity<>(UserDto.fromUser(user), HttpStatus.OK);
    }
    @PostMapping("/login")
    private Token login(@RequestBody LoginRequestDto requestUserDto){
        return null;
    }
    @PostMapping("/logout")
    private ResponseEntity<Void> signOut(@RequestBody LogoutRequestDto requestUserDto){
        return null;
    }

}
