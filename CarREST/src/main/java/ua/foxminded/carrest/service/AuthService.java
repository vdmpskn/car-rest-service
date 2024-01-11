package ua.foxminded.carrest.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.custom.response.Auth0TokenResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RestTemplate restTemplate;

    public Auth0TokenResponse getToken(String username, String password){
        String url = "https://dev-nwxbmgmx1lcshk46.us.auth0.com/oauth/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "grant_type=password" +
            "&username=" + username +
            "&password=" + password +
            "&audience=http://localhost:8080/" +
            "&client_id=LTeyfVdRTNVr0cLcWXvEJG3QBdhFsjRg" +
            "&client_secret=zdK6F6CCz95q8ofCu7Cd4RG-eI8pkh60d6JWzEx72q4xgNKt4FtQ2lj_ztZFlKUi";

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Auth0TokenResponse> responseEntity = restTemplate.postForEntity(url, requestEntity, Auth0TokenResponse.class);
        return responseEntity.getBody();
    }
}
