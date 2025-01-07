package com.prajoshwas.emailsender.dto;

import lombok.Data;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class EmailRequest {

    @NotNull
    @NotBlank
    private String from;

    @NotNull
    @NotBlank
    private List<String> reciepient;

    @NotNull
    @NotBlank
    private String subject;

    @NotNull
    @NotBlank
    private String text;
}
