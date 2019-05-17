package com.questionnaire.service;

import com.questionnaire.model.User;

public interface MailService {

    void sendActivationKeyToUser(User user);

    void notifyAboutChangingPassword(User user);

    void sendRestoredPasswordToUser(User user, String password);
}
