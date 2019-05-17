package com.questionnaire.web.rest.VM;

import javax.validation.constraints.Size;

public class EmailVM {

    @Size(min = 5, max = 50)
    private String email;

    public EmailVM() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
