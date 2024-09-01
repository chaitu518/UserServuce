package com.example.userservice.dtos;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {

    private String name;
    private String email;
    @ManyToMany
    private List<Role> roles;
    private boolean isVerified;
    public static UserDto fromUser(User user) {
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setVerified(user.isVerified());
        dto.setRoles(user.getRoles());
        dto.setVerified(user.isVerified());
        return dto;
    }

}
