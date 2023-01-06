package org.example.mappers;

import org.example.models.entities.Account;
import org.example.models.entities.Boat;
import org.example.models.requests.BoatRequest;
import org.example.models.responses.BoatResponse;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class BoatMapper {

    /**
     * Map BoatRequest to Boat entity.
     *
     * @param request   the boat request
     * @param createdAt the creation date
     * @param updatedAt the updated date
     * @return a boat
     */
    public Boat mapBoatRequestToBoat(BoatRequest request, Account account, OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        return Boat.builder()
                .name(request.getName())
                .description(request.getDescription())
                .year(request.getYear())
                .manufacturer(request.getManufacturer())
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }

    public BoatResponse mapBoatToBoatResponse(Boat boat) {
        return BoatResponse.builder()
                .id(boat.getId())
                .name(boat.getName())
                .description(boat.getDescription())
                .year(boat.getYear())
                .manufacturer(boat.getManufacturer())
                .createdAt(boat.getCreatedAt())
                .updatedAt(boat.getUpdatedAt())
                .build();
    }

}
