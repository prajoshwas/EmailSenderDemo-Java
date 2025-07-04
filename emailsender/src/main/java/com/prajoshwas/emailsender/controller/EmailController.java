package com.prajoshwas.emailsender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajoshwas.emailsender.service.EmailService;
import com.prajoshwas.emailsender.dto.EmailRequest;
import com.prajoshwas.emailsender.dto.EmailResponse;

@RestController
@RequestMapping(value = "/v1/emailSender")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/email/test/send")
    private ResponseEntity<EmailResponse> sendTestEmail(@RequestBody EmailRequest emailRequest) {

        EmailResponse emailResponse = emailService.sendEmail(emailRequest);

        return new ResponseEntity<EmailResponse>(emailResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/test/response")
    private ResponseEntity<EmailResponse> testEmail() {

        EmailResponse emailResponse = EmailResponse.builder()
                .code("200")
                .message("TEST")
                .build();

        return new ResponseEntity<EmailResponse>(emailResponse, HttpStatus.OK);
    }

    @PostMapping(value = "/jms-prod/email/send")
    private ResponseEntity<EmailResponse> sendProdEmail(@RequestBody EmailRequest emailRequest) {

        EmailResponse emailResponse = emailService.sendProdEmail(emailRequest);

        return new ResponseEntity<EmailResponse>(emailResponse, HttpStatus.OK);
    }

}
