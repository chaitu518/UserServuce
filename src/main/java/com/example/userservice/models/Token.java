package com.example.userservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@Entity(name = "tokens")
public class Token extends BaseModel{
    @Lob
    @Column(columnDefinition = "TEXT")
    private String value;
    private Date isExpiredAt;
    @ManyToOne
    private User user;
}
