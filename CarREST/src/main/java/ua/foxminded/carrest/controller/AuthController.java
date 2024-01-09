package ua.foxminded.carrest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.custom.response.Auth0TokenResponse;
import ua.foxminded.carrest.custom.response.AuthRequest;
import ua.foxminded.carrest.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @GetMapping("/getToken")
    public Auth0TokenResponse getToken(@RequestBody AuthRequest authRequest) {

        return authService.getToken(authRequest.getUsername(), authRequest.getPassword());
    }

}
