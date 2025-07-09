package com.prajoshwas.emailsender.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "emailTransactionId")
    private String emailTransactionId;
    @Column(name = "sender")
    private String sender;
    @Column(name = "recipients")
    private List<String> to;
    @Column(name = "emailStatus")
    private int emailSentStatus;
    @Column(name = "dateSent")
    private String sentDate;

}
