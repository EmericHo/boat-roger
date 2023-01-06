package org.example.models.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
public class AccountRequest {
    
    @Size(min = 8)
    private String password;

    @NotEmpty
    @Size(min = 3)
    private String userName;

}
