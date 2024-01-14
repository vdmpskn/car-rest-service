package ua.foxminded.carrest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import ua.foxminded.carrest.custom.exceptions.AuthException;
import ua.foxminded.carrest.custom.response.Auth0TokenResponse;
import ua.foxminded.carrest.custom.response.AuthRequest;
import ua.foxminded.carrest.service.AuthService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    private MockWebServer mockWebServer;

    @Before
    public void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        when(authService.getToken(any(), any()))
            .thenReturn(new Auth0TokenResponse("mocked_token",3600, "bearer"));
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void shouldGetToken_ValidCredentials() throws Exception {
        AuthRequest authRequest = new AuthRequest("test_user", "test_password");
        String requestBody = objectMapper.writeValueAsString(authRequest);

        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(200)
            .setBody("{\"token\": \"mocked_token\", \"tokenType\": \"bearer\"}")
            .setHeader("Content-Type", "application/json"));

        mockMvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.access_token").value("mocked_token"));
    }

    @Test
    public void shouldNotGetToken_AuthServiceError() throws Exception {
        AuthRequest authRequest = new AuthRequest("test_user", "test_password");
        String requestBody = objectMapper.writeValueAsString(authRequest);

        when(authService.getToken(any(), any()))
            .thenThrow(new RuntimeException(new AuthException("Authentication failed")));

        mockMvc.perform(post("/login")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadGateway())
            .andExpect(jsonPath("$.error").value("Authentication failed"));
    }
}



