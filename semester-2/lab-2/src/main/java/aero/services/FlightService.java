package aero.services;

import aero.dto.FlightDTO;
import aero.mapper.FlightMapper;
import aero.models.Flight;
import aero.repositories.FlightRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Room service class
 */
@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<FlightDTO> findAllFlights() {
        return flightRepository.findAll()
                .stream().map(FlightMapper::toDTO).collect(Collectors.toList());
    }

    public Flight findByFlightId(Long id) throws Exception {
        return flightRepository.findById(id)
                .orElseThrow(() -> new Exception("Could not find flight by id"));
    }

}
