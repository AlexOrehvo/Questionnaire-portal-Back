package com.questionnaire.service.impl;

import com.questionnaire.model.User;
import com.questionnaire.repository.UserRepository;
import com.questionnaire.security.SecurityUtils;
import com.questionnaire.service.MailService;
import com.questionnaire.service.UserService;
import com.questionnaire.service.dto.UserDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private MailService mailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    public User registerUser(UserDTO userDTO, String password) {
        userRepository.findByEmail(userDTO.getEmail().toLowerCase()).ifPresent(existingUser -> {
            System.out.println("User already exists.");
            throw new NullPointerException();
        });

        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);

        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setPhoneNumber(userDTO.getPhoneNumber());
        newUser.setActivated(false);
        newUser.setActivationKey(RandomStringUtils.randomAlphabetic(20));

        newUser = userRepository.save(newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserWithAuthorities() {
        String email = SecurityUtils.getCurrentUserEmail();
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> activateRegistration(String key) {
        return userRepository.findByActivationKey(key)
                .map( user -> {
                    user.setActivationKey(null);
                    user.setActivated(true);
                    userRepository.save(user);
                    return user;
                });
    }

    @Override
    public User saveUser(UserDTO userDTO) {
        User user = userRepository.getOne(userDTO.getId());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return userRepository.save(user);
    }

    @Override
    public void changePassword(String currentClearTextPassword, String newPassword) {
        String email = SecurityUtils.getCurrentUserEmail();
        userRepository.findByEmail(email).ifPresent(user -> {
            String currentEncryptedPassword = user.getPassword();
            if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                throw new RuntimeException("123");
            }

            String encryptedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            mailService.notifyAboutChangingPassword(user);
        });
    }

    @Override
    public void restorePassword(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            String newClearTextPassword = RandomStringUtils.randomAlphabetic(8);
            String newEncryptedPassword = passwordEncoder.encode(newClearTextPassword);
            user.setPassword(newEncryptedPassword);
            userRepository.save(user);
            mailService.sendRestoredPasswordToUser(user, newClearTextPassword);
        });
    }
}
