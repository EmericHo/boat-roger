package org.example.models.responses;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class AccountResponse {
    private String id;

    private String password;

    private String userName;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
