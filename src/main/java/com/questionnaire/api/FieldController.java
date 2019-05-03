package com.questionnaire.api;

import com.questionnaire.model.Field;
import com.questionnaire.service.FieldService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class FieldController {

    private FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping("/")
    public String saveField(@Valid @RequestBody Field field) {
        if (field.getId() != null) {
            throw new RuntimeException("This field's id is exists");
        }

        Field savedField = fieldService.save(field);
        return savedField.toString();
    }
}
