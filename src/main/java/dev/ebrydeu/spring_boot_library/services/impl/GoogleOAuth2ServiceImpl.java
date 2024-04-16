package dev.ebrydeu.spring_boot_library.services.impl;

import dev.ebrydeu.spring_boot_library.domain.entities.Role;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * JUST FOR TESTING
 * */
@Service
@RequiredArgsConstructor
public class GoogleOAuth2ServiceImpl extends DefaultOAuth2UserService {
    private final UserRepository repository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        User user = create(oAuth2User);
        repository.save(user);
        return oAuth2User;
    }

    private User create(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        User user = new User();

        user.setEmail(attributes.get("email").toString());
        user.setFirstName(attributes.get("given_name").toString());
        user.setLastName(attributes.get("family_name").toString());
        user.setAvatar(attributes.get("picture").toString());
        user.setUserName(attributes.get("name").toString());
        user.setRole(Role.ADMIN);

        return user;
    }
}
