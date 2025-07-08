package com.prajoshwas.emailsender.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prajoshwas.emailsender.entity.AuditLogRequest;

public interface AuditLogRepository extends JpaRepository<AuditLogRequest, String> {

}
