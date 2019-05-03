package com.questionnaire.api;

import com.questionnaire.model.Field;
import com.questionnaire.service.FieldService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FieldController {

    private FieldService fieldService;

    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @GetMapping("/fields")
    public List<Field> getAllFields() {
        return fieldService.getAll();
    }

    @GetMapping("/fields/{id}")
    public Field getField(@PathVariable Long id) {
        return fieldService.getOneById(id);
    }

    @PostMapping("/fields")
    public String saveField(@Valid @RequestBody Field field) {
        if (field.getId() != null) {
            throw new RuntimeException("This field's id is exists");
        }

        Field savedField = fieldService.save(field);
        return savedField.toString();
    }

    @PutMapping("/fields")
    public Field updateField(@Valid @RequestBody Field field) {
        if (field.getId() == null) {
            throw new RuntimeException("Id == null");
        }

        return fieldService.save(field);
    }

    @DeleteMapping("/fields/{id}")
    public String deleteField(@PathVariable Long id) {
        fieldService.delete(id);
        return "Field was deleted";
    }
}
