package com.questionnaire.service;

import com.questionnaire.model.User;
import com.questionnaire.service.dto.UserDTO;

import java.util.Optional;

public interface UserService {

    User registerUser(UserDTO userDTO, String password);

    Optional<User> getUserWithAuthorities();
}
