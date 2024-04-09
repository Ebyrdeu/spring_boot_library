package dev.ebrydeu.spring_boot_library.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

@TestConfiguration
public class SecurityConfigTest {
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return registrationId -> ClientRegistration
                .withRegistrationId("testId")
                .clientSecret("testSecret")
                .build();
    }
}

