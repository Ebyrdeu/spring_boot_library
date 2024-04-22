package dev.ebrydeu.spring_boot_library.services.impl.github;

import dev.ebrydeu.spring_boot_library.domain.dto.GithubEmailDto;
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

        Integer githubId = (Integer) attributes.get("id");
        User existingUser = userRepository.findByGithubId(githubId);

        if (existingUser == (null)) {
            User user = createUser(attributes, request);
            userRepository.save(user);
        }
        return oAuth2User;
    }

    private User createUser(Map<String, Object> attributes, OAuth2UserRequest request) {
        OAuth2AccessToken at = request.getAccessToken();
        User user = new User();
        user.setUserName((String) attributes.get("login"));
        user.setProfileImage((String) attributes.get("avatar_url"));
        user.setFirstName((String) attributes.get("first_name"));
        user.setLastName((String) attributes.get("last_name"));
        user.setGithubId((Integer) attributes.get("id"));

        User.UserBuilder userBuilder = User.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .profileImage(user.getProfileImage())
                .githubId(user.getGithubId());
                //.role(Role.ROLE_USER);


        if (user.getEmail() != null) {
            userBuilder.email(user.getEmail());
        }

        user = userBuilder.build();

        List<GithubEmailDto> emails = githubService.getEmails(at);

        emails.stream()
                .filter(GithubEmailDto::primary)
                .map(GithubEmailDto::email)
                .findFirst()
                .ifPresent(user::setEmail);

        return user;
    }
}
