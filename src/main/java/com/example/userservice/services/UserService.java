package com.example.userservice.services;

import com.example.userservice.exceptions.InvalidPasswordException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserService {
    public User signUp(String name,String email,String password) throws JsonProcessingException;
    public Token login(String email, String password) throws InvalidPasswordException;
    public void logout(String token);
    public User validateToken(String token);

}
