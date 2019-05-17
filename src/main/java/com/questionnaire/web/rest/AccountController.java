package com.questionnaire.web.rest;

import com.questionnaire.message.JwtResponse;
import com.questionnaire.model.User;
import com.questionnaire.security.jwt.TokenProvider;
import com.questionnaire.service.MailService;
import com.questionnaire.service.UserService;
import com.questionnaire.service.dto.PasswordChangeDTO;
import com.questionnaire.service.dto.UserDTO;
import com.questionnaire.web.rest.VM.EmailVM;
import com.questionnaire.web.rest.VM.LoginVM;
import com.questionnaire.web.rest.VM.ManagedUserVM;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/account")
public class AccountController {

    private AuthenticationManager authenticationManager;

    private TokenProvider tokenProvider;

    private UserService userService;

    private MailService mailService;

    public AccountController(
            AuthenticationManager authenticationManager,
            TokenProvider tokenProvider,
            UserService userService,
            MailService mailService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public String registerAccount(@Valid @RequestBody ManagedUserVM managedUserVM) {
        User user = userService.registerUser(managedUserVM, managedUserVM.getPassword());
        mailService.sendActivationKeyToUser(user);
        return "User was registered";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authorize(@Valid @RequestBody LoginVM loginVM) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginVM.getEmail(), loginVM.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        String jwt = tokenProvider.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @GetMapping("/")
    public User getAccount() {
        Optional<User> userOptional = userService.getUserWithAuthorities();
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new RuntimeException("123");
        }
    }

    @PutMapping("/")
    public User updateAccount(@Valid @RequestBody UserDTO userDTO) {
        return this.userService.saveUser(userDTO);
    }

    @PostMapping("/change-password")
    public void changePassword(@Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {
        userService.changePassword(passwordChangeDTO.getCurrentPassword(), passwordChangeDTO.getNewPassword());
    }

    @GetMapping("/activate/{key}")
    public void activate(@PathVariable String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new RuntimeException("No user was found for this activation key");
        }
    }

    @PostMapping("/forgot-password")
    public void forgetPassword(@Valid @RequestBody EmailVM emailVM) {
        userService.restorePassword(emailVM.getEmail());
    }
}
