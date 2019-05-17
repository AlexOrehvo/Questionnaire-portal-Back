package com.questionnaire.service.impl;

import com.questionnaire.model.User;
import com.questionnaire.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String username;

    public MailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    @Override
    public void sendActivationKeyToUser(User user) {
        String message = String.format(
          "Hello, %s! \n" +
                  "Welcome to Questionnaire. Please, visit next link: http://localhost:8080/account/activate/%s",
                user.getEmail(),
                user.getActivationKey()
        );

        sendMessage(user.getEmail(), "Activation code", message);
    }

    @Async
    @Override
    public void notifyAboutChangingPassword(User user) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Password was changed!",
                user.getEmail()
        );

        sendMessage(user.getEmail(), "Change password", message);
    }

    @Async
    @Override
    public void sendRestoredPasswordToUser(User user, String password) {
        String message = String.format(
                "Hello, %s! \n" +
                        "Your new password: %s \n" +
                        "You can change it later.",
                user.getEmail(),
                password
        );

        sendMessage(user.getEmail(), "Restore password", message);
    }

    private void sendMessage(String emailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(username);
        mailMessage.setTo(emailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        javaMailSender.send(mailMessage);
    }
}
