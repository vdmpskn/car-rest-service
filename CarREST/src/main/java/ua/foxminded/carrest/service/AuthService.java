package ua.foxminded.carrest.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.custom.exceptions.AuthException;
import ua.foxminded.carrest.custom.response.Auth0TokenResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${auth-service.audience}")
    private String audience;

    @Value("${auth-service.client-id}")
    private String clientId;

    @Value("${auth-service.client-secret}")
    private String clientSecret;

    private final RestTemplate restTemplate;

    public Auth0TokenResponse getToken(String username, String password) {
        String url = ("https://dev-nwxbmgmx1lcshk46.us.auth0.com/oauth/token");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=password" +
            "&username=" + username +
            "&password=" + password +
            "&audience=" + audience +
            "&client_id=" + clientId +
            "&client_secret=" + clientSecret;

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<Auth0TokenResponse> responseEntity = restTemplate.postForEntity(url, requestEntity, Auth0TokenResponse.class);
            return responseEntity.getBody();
        }
        catch (Exception ex) {
            throw new AuthException("Authentication failed");
        }

    }
}
