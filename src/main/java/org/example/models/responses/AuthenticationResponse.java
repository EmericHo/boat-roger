package org.example.models.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    private String accessToken;

    private String tokenType;

    private int expiresIn;

}
