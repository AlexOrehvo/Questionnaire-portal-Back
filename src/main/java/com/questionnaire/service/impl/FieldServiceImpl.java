package com.questionnaire.service.impl;

import com.questionnaire.model.Field;
import com.questionnaire.repository.FieldRepository;
import com.questionnaire.service.FieldService;
import com.questionnaire.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldServiceImpl implements FieldService {

    private UserService userService;

    private FieldRepository fieldRepository;

    public FieldServiceImpl(UserService userService, FieldRepository fieldRepository) {
        this.userService = userService;
        this.fieldRepository = fieldRepository;
    }

    @Override
    public List<Field> getAll() {
        return null;
    }

    @Override
    public Field getOneById(Long id) {
        return null;
    }

    @Override
    public Field save(Field field) {
        if (field.getId() == null) {
            userService.getUserWithAuthorities().ifPresent(field::setUser);
        }

        System.out.println(field.getUser().getEmail());
        //return fieldRepository.save(field);
        return field;
    }

    @Override
    public void delete(Long id) {

    }
}
