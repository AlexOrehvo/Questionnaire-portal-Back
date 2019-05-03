package com.questionnaire.service.impl;

import com.questionnaire.model.User;
import com.questionnaire.repository.UserRepository;
import com.questionnaire.security.SecurityUtils;
import com.questionnaire.service.UserService;
import com.questionnaire.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl() {

    }

    @Override
    public User registerUser(UserDTO userDTO, String password) {
        userRepository.findByEmail(userDTO.getEmail().toLowerCase()).ifPresent(existingUser -> {
            System.out.println("User already exists.");
            throw new NullPointerException();
        });

        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);

        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setActivated(true);/* change on false */

        newUser = userRepository.save(newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserWithAuthorities() {
        String email = SecurityUtils.getCurrentUserEmail();
        return userRepository.findByEmail(email);
    }
}
