package com.prajoshwas.emailsender.dto.mailtrap;

import java.util.List;

import lombok.Data;

@Data
public class MailTrapResponse {

    private Boolean success;
    private List<String> message_ids;
}
