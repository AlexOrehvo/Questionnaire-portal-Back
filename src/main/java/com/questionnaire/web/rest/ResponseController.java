package com.questionnaire.web.rest;

import com.questionnaire.model.Response;
import com.questionnaire.repository.FieldRepository;
import com.questionnaire.service.ResponseService;
import com.questionnaire.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ResponseController {

    private FieldRepository fieldRepository;

    private ResponseService responseService;

    private UserService userService;

    public ResponseController(FieldRepository fieldRepository, ResponseService responseService, UserService userService) {
        this.fieldRepository = fieldRepository;
        this.responseService = responseService;
        this.userService = userService;
    }

    @GetMapping("/responses")
    public List<Response> getAllResponses() {
        return responseService.getAll();
    }

    @PostMapping("/responses")
    public void saveResponse(@RequestBody(required = false) Response response) {
        if (response.getId() != null) {
            throw new RuntimeException("This field's id is exists");
        }

        responseService.saveResponse(response);
    }

    @DeleteMapping("/responses/{id]")
    public void deleteResponse(@PathVariable Long id) {
        responseService.deleteResponse(id);
    }
}
