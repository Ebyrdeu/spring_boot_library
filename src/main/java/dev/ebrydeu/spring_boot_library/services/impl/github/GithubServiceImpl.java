package dev.ebrydeu.spring_boot_library.services.impl.github;

import dev.ebrydeu.spring_boot_library.domain.dto.GithubEmailDto;
import dev.ebrydeu.spring_boot_library.services.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {

    private final RestClient restClient;

    @Override
    @Retryable
    public List<GithubEmailDto> getEmails(OAuth2AccessToken at) {
        return restClient.get()
                .uri("https://api.github.com/user/emails")
                .headers(headers -> headers.setBearerAuth(at.getTokenValue()))
                .accept(APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
