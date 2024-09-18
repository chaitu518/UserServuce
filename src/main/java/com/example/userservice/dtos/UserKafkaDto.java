package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserKafkaDto {
    String From;
    String To;
    String sub;
    String Message;
}
