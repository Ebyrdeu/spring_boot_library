package dev.ebrydeu.spring_boot_library.services.impl.github;

import dev.ebrydeu.spring_boot_library.domain.dto.GithubEmailDto;
import dev.ebrydeu.spring_boot_library.domain.entities.Role;
import dev.ebrydeu.spring_boot_library.domain.entities.User;
import dev.ebrydeu.spring_boot_library.repositories.UserRepository;
import dev.ebrydeu.spring_boot_library.services.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GithubOAuth2ServiceImpl extends DefaultOAuth2UserService {

    private final GithubService githubService;
    private final UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        Integer userId = (Integer) attributes.get("id");
        Optional<User> existingUser = userRepository.findByGithubId(userId);

        if (existingUser.isEmpty()) {
            User user = createUser(attributes, request);
            userRepository.save(user);
        }
        return oAuth2User;
    }

    private User createUser(Map<String, Object> attributes, OAuth2UserRequest request) {
        OAuth2AccessToken at = request.getAccessToken();
        String username = (String) attributes.get("login");
        String avatarUrl = (String) attributes.get("avatar_url");
        String name = (String) attributes.get("name");
        String email = (String) attributes.get("email");
        Integer githubId = (Integer) attributes.get("id");

        User.UserBuilder userBuilder = User.builder()
                .username(username)
                .avatar(avatarUrl)
                .githubId(githubId)
                .role(Role.ROLE_USER);

        if (name != null) {
            String[] nameParts = name.split(" ");
            userBuilder.firstName(nameParts.length > 0 ? nameParts[0] : "");
            userBuilder.lastName(nameParts.length > 1 ? nameParts[1] : "");
        }

        if (email != null) {
            userBuilder.email(email);
        }

        User user = userBuilder.build();

        List<GithubEmailDto> emails = githubService.getEmails(at);

        emails.stream()
                .filter(GithubEmailDto::primary)
                .map(GithubEmailDto::email)
                .findFirst()
                .ifPresent(user::setEmail);

        return user;
    }
}
