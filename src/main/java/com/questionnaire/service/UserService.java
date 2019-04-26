package com.questionnaire.service;

import com.questionnaire.model.User;
import com.questionnaire.service.dto.UserDTO;

public interface UserService {

    User registerUser(UserDTO userDTO, String password);
}
