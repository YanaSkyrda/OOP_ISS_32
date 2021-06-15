package aero.controllers;

import aero.dto.FlightDTO;
import aero.services.FlightService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@RequestMapping("/flight")
@CrossOrigin("http://localhost:4200")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping()
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<List<FlightDTO>> findAllFlights() {

        return ResponseEntity.ok(flightService.findAllFlights());
    }
}
