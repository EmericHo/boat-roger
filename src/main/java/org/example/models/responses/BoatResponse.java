package org.example.models.responses;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Builder
public class BoatResponse {
    private String id;

    private String name;

    private String description;

    private String manufacturer;

    private Integer year;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}