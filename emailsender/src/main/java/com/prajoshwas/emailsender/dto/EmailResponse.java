package com.prajoshwas.emailsender.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailResponse {

    private String status;
    private String code;
    private String message;
}
