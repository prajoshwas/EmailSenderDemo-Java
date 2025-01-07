package com.prajoshwas.emailsender.dto.mailtrap;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@EqualsAndHashCode
public class MailTrapTestRequest {

    @NotNull
    @NotBlank
    private List<SendTo> to;

    private SendFrom from;

    @Data
    public static class SendFrom {

        @NotNull
        @NotBlank
        private String email;

        @NotNull
        @NotBlank
        private String name;
    }

    @Data
    public static class SendTo {

        @NotNull
        @NotBlank
        private String email;
    }


}
