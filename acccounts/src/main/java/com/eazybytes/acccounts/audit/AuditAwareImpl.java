package com.eazybytes.acccounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * retrieves the current auditor (the user or system that is making changes)
 * when an entity is being persisted or updated
 */
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware <String>{
    /**
     * @return
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNTS_MS");
    }
}
