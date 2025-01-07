package com.prajoshwas.emailsender.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "mailtrap.smtp")
public class EmailSenderConfig {

    private String host;
    private String port;
    private String username;
    private String password;
    private String url;
}
