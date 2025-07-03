package com.prajoshwas.emailsender.service.impl;

import java.util.Objects;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.prajoshwas.emailsender.config.EmailSenderConfig;
import com.prajoshwas.emailsender.dto.EmailRequest;
import com.prajoshwas.emailsender.dto.EmailResponse;
import com.prajoshwas.emailsender.dto.mailtrap.MailTrapResponse;
import com.prajoshwas.emailsender.service.EmailService;
import com.prajoshwas.emailsender.utility.GenericWebClientBuilder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    GenericWebClientBuilder genericWebClientBuilder;

    @Autowired
    EmailSenderConfig emailSenderConfig;

    @Override
    public EmailResponse sendEmail(EmailRequest emailRequest) {

        EmailResponse emailResponse = null;

        try {

            WebClient webClient = genericWebClientBuilder.buildWebClient();

            MailTrapResponse mailTrapResponse = performTestSend(webClient, emailRequest);

            if (BooleanUtils.isTrue(mailTrapResponse.getSuccess())
                    && !Objects.isNull(mailTrapResponse.getMessage_ids())) {

                emailResponse = EmailResponse.builder()
                        .status(HttpStatus.OK.toString())
                        .message("Email successfully Sent")
                        .code("200")
                        .build();

            }

        } catch (WebClientResponseException e) {

            log.error("Unexpected Exception Occurred", e);
            if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                emailResponse = EmailResponse.builder()
                        .status(HttpStatus.BAD_REQUEST.toString())
                        .message("Unathorized Email Request")
                        .code("401")
                        .build();
            }
        } catch (Exception e) {
            log.error("Unexpected Exception Occurred", e);
            emailResponse = EmailResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                    .message("Unathorized Email Request")
                    .code("500")
                    .build();
        }

        return emailResponse;
    }

    private MailTrapResponse performTestSend(WebClient webClient, EmailRequest emailRequest) {

        MailTrapResponse mailTrapResponse = webClient.post().uri(emailSenderConfig.getUrl())
                .body(Mono.just(emailRequest), EmailRequest.class)
                .retrieve()
                .bodyToMono(MailTrapResponse.class)
                .block();

        return mailTrapResponse;
    }

    @Override
    public EmailResponse sendProdEmail(EmailRequest emailRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendProdEmail'");
    }
}
