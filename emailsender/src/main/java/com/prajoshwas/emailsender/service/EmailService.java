package com.prajoshwas.emailsender.service;

import com.prajoshwas.emailsender.dto.EmailRequest;
import com.prajoshwas.emailsender.dto.EmailResponse;

public interface EmailService {

    EmailResponse sendEmail(EmailRequest emailRequest);

    EmailResponse sendProdEmail(EmailRequest emailRequest);
}
