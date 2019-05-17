package com.questionnaire.web.rest.VM;

import javax.validation.constraints.NotNull;

public class LoginVM {

    @NotNull
    private String email;

    @NotNull
    private String password;

    private Boolean rememberMe;

    public LoginVM() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
