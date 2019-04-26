package com.questionnaire.service;

import com.questionnaire.model.Response;

import java.util.List;

public interface ResponseService {

    List<Response> getAll();

    void saveResponse(Response response);
}
