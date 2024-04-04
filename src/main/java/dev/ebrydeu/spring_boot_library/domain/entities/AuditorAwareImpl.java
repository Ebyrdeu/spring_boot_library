package dev.ebrydeu.spring_boot_library.domain.entities;

import lombok.NonNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    @NonNull
    public  Optional<String> getCurrentAuditor() {
        //todo replace with dynamic username after implementing security;
        return Optional.of("testUser");
    }
}
