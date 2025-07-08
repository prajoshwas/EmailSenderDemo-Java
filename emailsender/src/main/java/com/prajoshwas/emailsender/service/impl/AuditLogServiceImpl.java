package com.prajoshwas.emailsender.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prajoshwas.emailsender.entity.AuditLogRequest;
import com.prajoshwas.emailsender.repository.AuditLogRepository;
import com.prajoshwas.emailsender.service.AuditLogService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    AuditLogRepository auditLogRepository;

    @Override
    public Integer saveAuditLog(AuditLogRequest auditLogRequest) {

        Integer saved = null;

        try {

            log.info("Saving Audit Log Request");
            AuditLogRequest dto = auditLogRepository.save(auditLogRequest);

            if (!Objects.isNull(dto)) {
                log.info("Successfully Saved");
                saved = 1;
            }

        } catch (Exception ex) {
            log.error("Unexpected exception occurred {}", ex);
            saved = 0;
        }

        return saved;
    }

}
