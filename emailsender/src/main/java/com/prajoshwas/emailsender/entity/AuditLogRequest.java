package com.prajoshwas.emailsender.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "AuditLog")
public class AuditLogRequest {

    @Id
    private String emailTransactionId;
    @Column(name = "Sender")
    private String sender;
    @Column(name = "To")
    private List<String> to;
    @Column(name = "emailStatus")
    private int emailSentStatus;
    @Column(name = "dateSent")
    private String sentDate;

}
