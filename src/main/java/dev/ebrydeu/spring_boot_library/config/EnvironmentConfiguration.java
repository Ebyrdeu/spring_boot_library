package dev.ebrydeu.spring_boot_library.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("environment")
public class EnvironmentConfiguration {
    String mode;
}
