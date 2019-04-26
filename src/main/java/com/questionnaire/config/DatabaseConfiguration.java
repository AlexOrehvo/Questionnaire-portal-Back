package com.questionnaire.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.questionnaire.repository")
public class DatabaseConfiguration {
}
