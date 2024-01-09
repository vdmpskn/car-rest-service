package ua.foxminded.carrest.custom.response;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
