package com.prajoshwas.emailsender.dto;

import com.prajoshwas.emailsender.dto.mailtrap.MailTrapTestRequest;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class EmailRequest extends MailTrapTestRequest {


    @NotNull
    @NotBlank
    private String subject;

    @NotNull
    @NotBlank
    private String text;
}
