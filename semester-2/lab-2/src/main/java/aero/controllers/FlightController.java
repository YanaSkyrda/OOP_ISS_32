package aero.controllers;

import aero.dto.FlightDTO;
import aero.dto.TicketDTO;
import aero.models.Flight;
import aero.services.FlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.ExcludeSuperclassListeners;
import java.util.List;


@RestController
@Slf4j
@CrossOrigin("http://localhost:4200")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/flight")
    @PreAuthorize("isAnonymous()")
    public ResponseEntity<List<FlightDTO>> findAllFlights() {

        return ResponseEntity.ok(flightService.findAllFlights());
    }

    @PostMapping("/flight")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Flight> createNewFlight(@RequestBody FlightDTO flightDTO) {
        try {
            return ResponseEntity.ok(flightService.createFlight(flightDTO));
        } catch (Exception e) {
            log.error("Cannot create flight");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
}
