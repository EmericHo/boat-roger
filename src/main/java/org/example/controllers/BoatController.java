package org.example.controllers;

import org.example.models.requests.BoatRequest;
import org.example.models.responses.BoatResponse;
import org.example.services.BoatService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3001"}, methods = {POST, GET, DELETE, PATCH})
@RequestMapping(path = "/boats", produces = "application/json; charset=UTF-8")
public class BoatController {

    private final BoatService boatService;

    public BoatController(BoatService boatService) {
        this.boatService = boatService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoatResponse createBoat(@RequestBody @Valid BoatRequest boatRequest) {
        return boatService.createBoat(boatRequest);
    }

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoatResponse updateBoat(@PathVariable String id, @RequestBody @Valid BoatRequest boatRequest) {
        return boatService.updateBoat(id, boatRequest);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoatResponse deleteBoat(@PathVariable String id) {
        return boatService.deleteBoat(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BoatResponse> getBoats() {
        return boatService.getAllBoats();
    }

}
