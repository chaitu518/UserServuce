package com.example.userservice.services;

import com.example.userservice.KafkaConfig;
import com.example.userservice.dtos.UserKafkaDto;
import com.example.userservice.exceptions.InvalidPasswordException;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepository;
import com.example.userservice.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenRepository tokenRepository;
    private KafkaConfig kafkaConfig;
    private ObjectMapper objectMapper;
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,TokenRepository tokenRepository,KafkaConfig kafkaConfig,ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaConfig = kafkaConfig;
        this.objectMapper = objectMapper;
    }
    @Override
    public User signUp(String name, String email, String password) throws JsonProcessingException {

        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        UserKafkaDto userKafkaDto = new UserKafkaDto();
        userKafkaDto.setTo(user.getEmail());
        userKafkaDto.setFrom("tempm6176@gmail.com");
        userKafkaDto.setSub("Welcome to Universal shopping");
        userKafkaDto.setMessage("Thank you for registering user");
        kafkaConfig.sendMessage("sendEmail",objectMapper.writeValueAsString(userKafkaDto));
        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) throws InvalidPasswordException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
           throw new UsernameNotFoundException("User not found");
        }
        User user = userOptional.get();
        if (!bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {
            throw new InvalidPasswordException("Bad credentials");
        }
        Token token = generateToken(user);
        return tokenRepository.save(token);
    }

    @Override
    public void logout(String token) {
        Token token1 = tokenRepository.findByValueAndDeleted(token,false);
        if (token1 == null) {
            throw new RuntimeException("Token not found");
        }
        token1.setDeleted(true);
        tokenRepository.save(token1);
    }

    @Override
    public User validateToken(String token) {
        Token token1 = tokenRepository.findByValueAndDeleted(token,false);
        if (token1 == null) {
            throw new RuntimeException("Token not found");
        }
        User user = token1.getUser();
        return user;
    }

    private Token generateToken(User user){
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime futureDateTime = currentDateTime.plusDays(30);
        Date expiryDate = Date.from(futureDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setIsExpiredAt(expiryDate);
        token.setUser(user);
        String tokenValue = RandomStringUtils.random(128, true, false);
        token.setValue(tokenValue);
        return token;
    }
}
