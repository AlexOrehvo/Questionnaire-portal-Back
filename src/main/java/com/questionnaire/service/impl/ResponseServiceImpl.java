package com.questionnaire.service.impl;

import com.questionnaire.model.Response;
import com.questionnaire.repository.ResponseRepository;
import com.questionnaire.service.ResponseService;
import com.questionnaire.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseServiceImpl implements ResponseService {

    private ResponseRepository responseRepository;

    public ResponseServiceImpl(ResponseRepository responseRepository, UserService userService) {
        this.responseRepository = responseRepository;
    }

    @Override
    public List<Response> getAll() {
        return responseRepository.findAll();
    }

    @Override
    public Response saveResponse(Response response) {
        return responseRepository.save(response);
    }

    @Override
    public void deleteResponse(Long id) {
        responseRepository.deleteById(id);
    }
}
