package com.questionnaire.service;

import com.questionnaire.model.Response;

import java.util.List;

public interface ResponseService {

    List<Response> getAll();

    Response saveResponse(Response response);

    void deleteResponse(Long id);
}
