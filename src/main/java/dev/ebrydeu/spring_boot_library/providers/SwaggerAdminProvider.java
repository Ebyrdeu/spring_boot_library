package dev.ebrydeu.spring_boot_library.providers;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;

import java.util.Objects;

public class SwaggerAdminProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var name = authentication.getName();
        if (Objects.equals(name, "admin")) {
            var admin = User
                    .withUsername("admin")
                    .password("admin")
                    .roles("USER", "ADMIN").build();
            return UsernamePasswordAuthenticationToken.authenticated(
                    admin,
                    null,
                    admin.getAuthorities()
            );
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
