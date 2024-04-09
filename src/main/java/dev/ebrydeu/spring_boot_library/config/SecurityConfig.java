package dev.ebrydeu.spring_boot_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home","/oauth2/authorization/github","/user-profile-page", "/api/public/**").permitAll()
                        .requestMatchers("/api/user/**", "/user-profile-page").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/oauth2/authorization/github")
                        .defaultSuccessUrl("/user-profile-page")
                        .failureUrl("/home"))
                .logout(logout -> logout
                        .logoutSuccessUrl("/home").permitAll());

        return http.build();
    }

    //@Bean
    //public AuthenticationSuccessHandler loginSuccessHandler() {
      //  SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
      //  handler.setUseReferer(true);
      //  handler.setDefaultTargetUrl("/user");
     //   return handler;
   // }
}
