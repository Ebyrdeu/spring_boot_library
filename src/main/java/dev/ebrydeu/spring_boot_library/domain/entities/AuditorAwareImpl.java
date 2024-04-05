package dev.ebrydeu.spring_boot_library.domain.entities;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        // Placeholder for the current user
        return Optional.of("testUser");
    }
}