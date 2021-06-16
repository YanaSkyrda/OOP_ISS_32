package aero.services;

import aero.dto.FlightDTO;
import aero.dto.TicketDTO;
import aero.mapper.FlightMapper;
import aero.models.Flight;
import aero.models.Ticket;
import aero.repositories.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Room service class
 */
@Service
@Slf4j
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

    public Flight createFlight(FlightDTO flightDTO) throws Exception {
        try {
            Flight flight = FlightMapper.toEntity(flightDTO);
            return flightRepository.save(flight);

        } catch (Exception e) {
            log.error("User can't book flight");
            throw new Exception("Something went wrong.");
        }
    }

}
