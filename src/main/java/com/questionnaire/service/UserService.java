package com.questionnaire.service;

import com.questionnaire.model.User;
import com.questionnaire.service.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    User registerUser(UserDTO userDTO, String password);

    Optional<User> getUserWithAuthorities();

    Optional<User> activateRegistration(String key);

    User saveUser(UserDTO userDTO);

    void changePassword(String currentClearTextPassword, String newPassword);

    void restorePassword(String email);
}
