package aero.services;

import aero.dto.FlightDTO;
import aero.models.Flight;
import aero.repositories.FlightRepository;
import org.springframework.stereotype.Service;

/**
 * Room service class
 */
@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public FlightDTO findAllFlights() {
        return new FlightDTO(flightRepository.findAll());
    }

    public Flight findByFlightId(Long id) throws Exception {
        return flightRepository.findById(id)
                .orElseThrow(() -> new Exception("Could not find flight by id"));
    }

}
