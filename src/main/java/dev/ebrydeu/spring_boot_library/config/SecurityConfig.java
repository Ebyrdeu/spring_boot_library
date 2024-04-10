package dev.ebrydeu.spring_boot_library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;


@Configuration
public class SecurityConfig {

    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService;

    public SecurityConfig(OAuth2UserService<OAuth2UserRequest, OAuth2User> customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }
    @Bean
    public SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/web/home","/oauth2/authorization/github", "/api/public/**","/logout").permitAll()
                        .requestMatchers("/api/user/**", "/web/user-profile-page").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpoint ->
                                userInfoEndpoint
                                        .userService(customOAuth2UserService))
                        .successHandler(oauth2AuthenticationSuccessHandler()))
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/web/home")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .clearAuthentication(true)
                        .permitAll());
                        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler() {
        return new SimpleUrlAuthenticationSuccessHandler("/web/user-profile-page");
    }

    //@Bean
    //public AuthenticationSuccessHandler loginSuccessHandler() {
      //  SimpleUrlAuthenticationSuccessHandler handler = new SimpleUrlAuthenticationSuccessHandler();
      //  handler.setUseReferer(true);
      //  handler.setDefaultTargetUrl("/user");
     //   return handler;
   // }
}
