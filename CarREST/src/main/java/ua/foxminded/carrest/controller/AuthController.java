package ua.foxminded.carrest.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import ua.foxminded.carrest.custom.exceptions.AuthException;
import ua.foxminded.carrest.custom.response.Auth0TokenResponse;
import ua.foxminded.carrest.custom.response.AuthRequest;
import ua.foxminded.carrest.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Get authentication token")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Authentication successful",
            content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = Auth0TokenResponse.class))}),
        @ApiResponse(responseCode = "400", description = "Invalid request",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "401", description = "Authentication failed",
            content = @Content(mediaType = "application/json"))})
    public Auth0TokenResponse getToken(@RequestBody AuthRequest authRequest) {
        try {
            return authService.getToken(authRequest.getUsername(), authRequest.getPassword());
        }
        catch (AuthException ex) {
            throw new RuntimeException("Authentication failed", ex);
        }
    }
}
