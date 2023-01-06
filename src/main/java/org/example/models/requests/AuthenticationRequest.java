package org.example.models.requests;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;


@Data
@Builder
public class AuthenticationRequest {

    @NonNull
    private String userName;

    @NonNull
    private String password;

}
