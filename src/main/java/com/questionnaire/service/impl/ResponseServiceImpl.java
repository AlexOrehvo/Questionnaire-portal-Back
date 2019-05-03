package com.questionnaire.service.impl;

import com.questionnaire.model.Response;
import com.questionnaire.model.User;
import com.questionnaire.repository.ResponseRepository;
import com.questionnaire.service.ResponseService;
import com.questionnaire.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponseServiceImpl implements ResponseService {

    private ResponseRepository responseRepository;

    private UserService userService;

    public ResponseServiceImpl(ResponseRepository responseRepository, UserService userService) {
        this.responseRepository = responseRepository;
        this.userService = userService;
    }

    @Override
    public List<Response> getAll() {
        Optional<User> optionalUser = userService.getUserWithAuthorities();
        return optionalUser.map(user -> responseRepository.getAllByUserId(user.getId())).orElse(null);
    }

    @Override
    public void saveResponse(Response response) {
        userService.getUserWithAuthorities().ifPresent(response::setUser);
        responseRepository.save(response);
    }
}
