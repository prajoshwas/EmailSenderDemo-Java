package com.prajoshwas.emailsender.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.prajoshwas.emailsender.config.EmailSenderConfig;
import com.prajoshwas.emailsender.dto.EmailRequest;
import com.prajoshwas.emailsender.dto.EmailResponse;
import com.prajoshwas.emailsender.dto.mailtrap.MailTrapResponse;
import com.prajoshwas.emailsender.dto.mailtrap.MailTrapTestRequest.SendTo;
import com.prajoshwas.emailsender.entity.AuditLogRequest;
import com.prajoshwas.emailsender.service.AuditLogService;
import com.prajoshwas.emailsender.service.EmailService;
import com.prajoshwas.emailsender.utility.GenericWebClientBuilder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailSenderConfig emailSenderConfig;

    @Autowired
    private GenericWebClientBuilder genericWebClientBuilder;

    @Autowired
    private JavaMailSender jakartaEmailSender;

    @Autowired
    private AuditLogService auditLogService;

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

    @Override
    public EmailResponse sendJakartaMail(EmailRequest emailRequest) {

        EmailResponse response = null;
        Integer emailSentCount = 0;

        List<String> recipients = getRecipientsList(emailRequest.getTo());

        for (String recipient : recipients) {

            try {

                SimpleMailMessage mail = generateEmail(emailRequest, recipient);

                log.info("Sending E-mail to recipient");
                jakartaEmailSender.send(mail);

                log.info("Email Sent Successfully to {}", recipient);

                log.info("Saving emails sent to the audit logs");
                auditLogService.saveAuditLog(generateAuditRequest(emailRequest, recipients, 1));

                emailSentCount++;

            } catch (MailSendException mse) {

                log.error("Mail Sending Failed encountered {}", mse.getMessage());

                response = EmailResponse.builder()
                        .code("400")
                        .message("There is an issue sending the email")
                        .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .build();

                log.info("Saving emails not sent to the audit logs");
                auditLogService.saveAuditLog(generateAuditRequest(emailRequest, recipients, 0));

                break;

            } catch (Exception ex) {
                log.error("Unexpected Exception encountered {}", ex.getMessage());

                response = EmailResponse.builder()
                        .code("500")
                        .message("Unexpected Exception Occurred")
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .build();

                log.info("Saving emails not sent to the audit logs");
                auditLogService.saveAuditLog(generateAuditRequest(emailRequest, recipients, 0));

                break;
            }
        }

        log.info("Total E-mails sent {} out of {}", emailSentCount, emailRequest.getTo().size());
        return response;
    }

    private SimpleMailMessage generateEmail(EmailRequest emailRequest, String recipient) {

        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setFrom(emailRequest.getFrom().getEmail());
        mail.setTo(recipient);
        mail.setSubject(emailRequest.getSubject());
        mail.setText(emailRequest.getText());

        return mail;
    }

    private AuditLogRequest generateAuditRequest(EmailRequest emailRequest, List<String> recipients, int emailStatus) {

        AuditLogRequest auditLogRequest = AuditLogRequest.builder()
                .emailTransactionId(UUID.randomUUID().toString())
                .emailSentStatus(emailStatus)
                .sentDate(LocalDateTime.now())
                .sender(emailRequest.getFrom().getEmail())
                .to(recipients)
                .build();

        return auditLogRequest;
    }

    private List<String> getRecipientsList(List<SendTo> emailToList) {

        log.info("Extracting Email Recipients");
        return emailToList.stream().map(SendTo::getEmail).collect(Collectors.toList());
    }
}
