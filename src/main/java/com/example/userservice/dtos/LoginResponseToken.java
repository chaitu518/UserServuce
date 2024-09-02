package com.example.userservice.dtos;

import com.example.userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseToken {
    Token token;
}
