package com.example.userservice.services;

import com.example.userservice.models.User;

public interface UserService {
    public User signUp(String name,String email,String password);
}
