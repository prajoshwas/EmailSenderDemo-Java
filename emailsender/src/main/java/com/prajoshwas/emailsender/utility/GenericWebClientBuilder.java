package com.prajoshwas.emailsender.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import com.prajoshwas.emailsender.config.EmailSenderConfig;
import com.prajoshwas.emailsender.constants.Constants;

@Component
public class GenericWebClientBuilder {

    @Autowired
    EmailSenderConfig emailSenderConfig;

    public WebClient buildWebClient() {

        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(Constants.AUTHORIZATION, Constants.AUTH_BEARER + " " + emailSenderConfig.getToken())
                .build();

        return webClient;
    }
}
