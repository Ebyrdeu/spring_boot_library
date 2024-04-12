package dev.ebrydeu.spring_boot_library.services.githubservice;

import dev.ebrydeu.spring_boot_library.config.GitHubEmailConfig;
import dev.ebrydeu.spring_boot_library.domain.entities.Role;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.repositories.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    GitHubService gitHubService;
    public OAuth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2AccessToken accessToken = userRequest.getAccessToken();

        List<GitHubEmailConfig> result = gitHubService.getEmails(accessToken);

        String userName = (String) attributes.get("login");
        Optional<User> authenticatedUser = userRepository.findByGitHubId((Integer) attributes.get("id"));

        if (authenticatedUser.isEmpty()) {
            User user = new User();
            user.setUserName((String) attributes.get("login"));
            user.setFullName((String) attributes.get("name"));
            user.setProfilePicture((String) attributes.get("avatar_url"));
            user.setGitHubId((Integer) attributes.get("id"));
            user.setEmail(result.getFirst().email());
            user.setRole(Role.USER);
            String roleName = "ROLE_USER";
        }
        return oAuth2User;
    }
}
