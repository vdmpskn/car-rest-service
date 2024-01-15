package ua.foxminded.carrest.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ua.foxminded.carrest.custom.response.Auth0TokenResponse;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AuthService authService;

    @Test
    void getToken_shouldReturnTokenResponse_whenValidCredentials() {
        String username = "validUser";
        String password = "validPassword";
        Auth0TokenResponse expectedResponse = new Auth0TokenResponse();
        expectedResponse.setAccessToken("access_token");

        when(restTemplate.postForEntity(
            eq("https://dev-nwxbmgmx1lcshk46.us.auth0.com/oauth/token"),
            any(HttpEntity.class),
            eq(Auth0TokenResponse.class)))
            .thenReturn(ResponseEntity.ok(expectedResponse));

        Auth0TokenResponse actualResponse = authService.getToken(username, password);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    void getToken_shouldThrowException_whenRestTemplateThrowsException() {
        String username = "anyUser";
        String password = "anyPassword";
        RuntimeException expectedException = new RuntimeException("Error contacting Auth0");

        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(Auth0TokenResponse.class)))
            .thenThrow(expectedException);

        assertThrows(RuntimeException.class, () -> authService.getToken(username, password));
    }
}
