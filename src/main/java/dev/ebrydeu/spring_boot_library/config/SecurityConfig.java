package dev.ebrydeu.spring_boot_library.config;

import dev.ebrydeu.spring_boot_library.providers.SwaggerAdminProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] SWAGGER_PATHS = {"/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**", "/webjars/swagger-ui/**"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home", "/oauth2/authorization/github", "/user-profile-page", "/api/public/**", "/swagger/login", "swagger-login").permitAll()
                        .requestMatchers(SWAGGER_PATHS).hasRole("ADMIN")
                        .requestMatchers("/api/user/**", "/user-profile-page").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2.defaultSuccessUrl("/user-profile-page"))
                .httpBasic(withDefaults())
                .formLogin(formLogin -> {
                    formLogin.defaultSuccessUrl("/swagger-ui/index.html#/");
                    formLogin.loginPage("/swagger/login");
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
                .logout(logout -> logout.logoutSuccessUrl("/home").permitAll())
                .authenticationProvider(new SwaggerAdminProvider());

        return http.build();
    }


}
