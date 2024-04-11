package dev.ebrydeu.spring_boot_library.services.impl;

import dev.ebrydeu.spring_boot_library.domain.entities.Role;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public OAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String userName = (String) attributes.get("login");
        Optional<User> authenticatedUser = userRepository.findByUserName(userName);

        if (authenticatedUser.isEmpty()) {
            User user = createUser(attributes);
            userRepository.save(user);
        }

        String roleName = "ROLE_USER";
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(roleName));

        return new DefaultOAuth2User(authorities, attributes, "login");
    }

    private User createUser(Map<String, Object> attributes) {
        User user = new User();
        user.setUserName((String) attributes.get("login"));
        user.setFullName((String) attributes.get("name"));
        user.setProfilePicture((String) attributes.get("avatar_url"));
        user.setRole(Role.USER);
        return user;
    }
}
