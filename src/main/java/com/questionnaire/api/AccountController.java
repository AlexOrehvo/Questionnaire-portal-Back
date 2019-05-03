package com.questionnaire.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.questionnaire.api.VM.LoginVM;
import com.questionnaire.api.VM.ManagedUserVM;
import com.questionnaire.model.User;
import com.questionnaire.security.SecurityConstants;
import com.questionnaire.security.jwt.TokenProvider;
import com.questionnaire.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AuthenticationManager authenticationManager;

    private TokenProvider tokenProvider;

    private UserService userService;

    public AccountController(
            AuthenticationManager authenticationManager,
            TokenProvider tokenProvider,
            UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register")
    public String registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        return "User was registered";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTToken> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getEmail(), loginVM.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.createToken(authentication, rememberMe);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(SecurityConstants.AUTHORIZATION_HEADER, SecurityConstants.TOKEN_PREFIX + jwt);
        return new ResponseEntity<>(new JWTToken(jwt), httpHeaders, HttpStatus.OK);
    }

    static class JWTToken {

        private String idToken;

        JWTToken(String idToken) {
            this.idToken = idToken;
        }

        @JsonProperty("id_token")
        String getIdToken() {
            return idToken;
        }

        void setIdToken(String idToken) {
            this.idToken = idToken;
        }
    }
}
