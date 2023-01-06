package org.example.models.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BoatRequest {
    private String name;

    private String description;

    private String manufacturer;

    private Integer year;

}
