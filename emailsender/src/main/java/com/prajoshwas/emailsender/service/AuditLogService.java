package com.prajoshwas.emailsender.service;

import com.prajoshwas.emailsender.entity.AuditLogRequest;

public interface AuditLogService {

    Integer saveAuditLog(AuditLogRequest auditLogRequest);
}
