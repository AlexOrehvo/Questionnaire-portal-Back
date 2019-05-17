package com.questionnaire.web.websocket;

import com.questionnaire.model.Response;
import com.questionnaire.service.ResponseService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ResponseWebsocketController {

    private ResponseService responseService;

    public ResponseWebsocketController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @MessageMapping("/responses")
    @SendTo("/responses/onAdd")
    public Response getAllResponses(Response response) throws Exception {
        return responseService.saveResponse(response);
    }
}
