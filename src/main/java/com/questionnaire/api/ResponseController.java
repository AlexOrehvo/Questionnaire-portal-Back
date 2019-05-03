package com.questionnaire.api;

import com.questionnaire.model.Field;
import com.questionnaire.model.Response;
import com.questionnaire.model.User;
import com.questionnaire.repository.FieldRepository;
import com.questionnaire.service.ResponseService;
import com.questionnaire.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public ResponseEntity<Response> getAllResponses() {
        List<Field> fields = fieldRepository.findAll();
        Response response = new Response();
        Map<Field, String> map = new HashMap<>();
        Optional<User> user = userService.getUserWithAuthorities();

        for (Field field: fields) {
            map.put(field, field.getLabel());
        }

        response.setMap(map);
        user.ifPresent(response::setUser);

        System.out.println(response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/responses")
    public String saveResponses(@RequestBody(required = false) Response response) {
       /* if (response.getId() != null) {
            throw new RuntimeException("This field's id is exists");
        }

        responseService.saveResponse(response);*/
        return "Response";
    }
}
