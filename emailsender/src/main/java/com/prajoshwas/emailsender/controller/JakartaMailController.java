package com.prajoshwas.emailsender.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prajoshwas.emailsender.dto.EmailRequest;
import com.prajoshwas.emailsender.dto.EmailResponse;
import com.prajoshwas.emailsender.service.EmailService;

@RestController
@RequestMapping(value = "/v1/emailSender")
public class JakartaMailController {

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/basic/jkrta/sendEmail")
    private ResponseEntity<EmailResponse> sendJakartaEmail(@RequestBody EmailRequest request) {
        EmailResponse response = emailService.sendJakartaMail(request);

        return new ResponseEntity<EmailResponse>(response, HttpStatus.OK);
    }

}
