package org.example.controllers;import org.example.models.requests.AuthenticationRequest;import org.example.models.responses.AuthenticationResponse;import org.example.services.AuthenticationService;import org.springframework.http.HttpStatus;import org.springframework.web.bind.annotation.*;import javax.validation.Valid;import static org.springframework.web.bind.annotation.RequestMethod.POST;@RestController@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3001"}, methods = {POST})@RequestMapping(path = "/auth", produces = "application/json; charset=UTF-8")public class AuthenticationController {    private final AuthenticationService authenticationService;    public AuthenticationController(AuthenticationService authenticationService) {        this.authenticationService = authenticationService;    }    @PostMapping    @ResponseStatus(HttpStatus.ACCEPTED)    public AuthenticationResponse authenticate(@RequestBody @Valid AuthenticationRequest authenticationRequest) {        return authenticationService.withPassword(authenticationRequest.getUserName(), authenticationRequest.getPassword());    }}