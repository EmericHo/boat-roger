package org.example.services;

import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.BadRequestException;
import org.example.exceptions.NotFoundException;
import org.example.exceptions.errors.BoatError;
import org.example.mappers.BoatMapper;
import org.example.models.entities.Account;
import org.example.models.entities.Boat;
import org.example.models.requests.BoatRequest;
import org.example.models.responses.BoatResponse;
import org.example.repositories.BoatRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoatService {

    private BoatRepository boatRepository;

    private BoatMapper boatMapper;

    private Clock clock;

    public BoatService(BoatRepository boatRepository, BoatMapper boatMapper, Clock clock) {
        this.boatRepository = boatRepository;
        this.boatMapper = boatMapper;
        this.clock = clock;
    }

    /**
     * Create a boat.
     *
     * @param boatRequest the boat request
     * @return a boat response
     */
    public BoatResponse createBoat(BoatRequest boatRequest) {
        log.debug("Create boat with name: {} ", boatRequest.getName());

        if (Objects.isNull(boatRequest.getName())) {
            throw new BadRequestException(BoatError.BAD_REQUEST, "name");
        }

        // TODO : Should be in utils
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Boat boat = boatMapper.mapBoatRequestToBoat(boatRequest, account, OffsetDateTime.now(clock), OffsetDateTime.now(clock));
        Boat savedBoat = boatRepository.save(boat);
        return boatMapper.mapBoatToBoatResponse(savedBoat);
    }

    /**
     * Update a boat.
     *
     * @param id          the boat id
     * @param boatRequest the boat request
     * @return a boat response
     */
    public BoatResponse updateBoat(String id, BoatRequest boatRequest) {
        log.debug("Update boat with name: {} ", boatRequest.getName());

        Boat existingBoat = boatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(BoatError.NOT_FOUND, id));

        Boat updatedBoat = existingBoat.toBuilder()
                .name(Objects.nonNull(boatRequest.getName()) ? boatRequest.getName() : existingBoat.getName())
                .description(Objects.nonNull(boatRequest.getDescription()) ? boatRequest.getDescription() : existingBoat.getDescription())
                .year(Objects.nonNull(boatRequest.getYear()) ? boatRequest.getYear() : existingBoat.getYear())
                .manufacturer(Objects.nonNull(boatRequest.getManufacturer()) ? boatRequest.getManufacturer() : existingBoat.getManufacturer())
                .updatedAt(OffsetDateTime.now(clock))
                .build();
        Boat savedBoat = boatRepository.save(updatedBoat);
        return boatMapper.mapBoatToBoatResponse(savedBoat);
    }

    /**
     * Delete a boat.
     *
     * @param id the boat id
     * @return a boat response
     */
    public BoatResponse deleteBoat(String id) {
        log.debug("Update boat with name: {} ", id);

        // TODO: Should add delete restriction on user if it's needed and a better exception handler for account
        Boat existingBoat = boatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(BoatError.NOT_FOUND, id));

        boatRepository.delete(existingBoat);
        return boatMapper.mapBoatToBoatResponse(existingBoat);
    }

    /**
     * Gat all boats.
     *
     * @return a list of boats
     */
    public List<BoatResponse> getAllBoats() {
        log.debug("Get all boats");

        List<Boat> boats = boatRepository.findAll();
        return boats.stream()
                .map(boat -> boatMapper.mapBoatToBoatResponse(boat))
                .collect(Collectors.toList());
    }

}
