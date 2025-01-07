package com.prajoshwas.emailsender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "mailtrap.smtp.test")
public class EmailSenderConfig {

    private String token;
    private String url;
}
