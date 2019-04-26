package com.questionnaire.service;

import com.questionnaire.model.Field;

import java.util.List;

public interface FieldService {

    List<Field> getAll();

    Field getOneById(Long id);

    Field save(Field field);

    void delete(Long id);
}
