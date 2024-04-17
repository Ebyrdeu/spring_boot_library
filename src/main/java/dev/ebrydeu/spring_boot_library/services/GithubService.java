package dev.ebrydeu.spring_boot_library.services;

import dev.ebrydeu.spring_boot_library.domain.dto.GithubEmailDto;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.util.List;


public interface GithubService {

    List<GithubEmailDto> getEmails(OAuth2AccessToken at);
}
