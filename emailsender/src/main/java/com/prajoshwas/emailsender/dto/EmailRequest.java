package com.prajoshwas.emailsender.dto;

import com.prajoshwas.emailsender.dto.mailtrap.MailTrapTestRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class EmailRequest extends MailTrapTestRequest {

    @NotNull
    @NotBlank
    private String subject;

    @NotNull
    @NotBlank
    private String text;
}
