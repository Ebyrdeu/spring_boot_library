package dev.ebrydeu.spring_boot_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditorConfig {
    @Bean
    public AuditorAware<String> auditorProvider() {
        //todo return new SpringSecurityAuditorAware();
    return () -> Optional.of("admin");}
}
