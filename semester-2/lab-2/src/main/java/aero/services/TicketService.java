package aero.services;

import aero.dto.TicketDTO;
import aero.mapper.TicketMap;
import aero.models.Flight;
import aero.models.Ticket;
import aero.repositories.FlightRepository;
import aero.repositories.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Ticket service class
 */
@Service
@Slf4j
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketMap ticketMapper;
    private final FlightRepository flightRepository;

    public TicketService(TicketRepository ticketRepository,
                         TicketMap ticketMapper, FlightRepository flightRepository) {
        this.ticketRepository = ticketRepository;
        this.ticketMapper = ticketMapper;
        this.flightRepository = flightRepository;
    }

    public Ticket createTicket(TicketDTO ticketDTO) throws Exception {
        try {
            Ticket ticket = ticketMapper.toEntity(ticketDTO);
            return ticketRepository.save(ticket);

        } catch (Exception e) {
            log.error("User can't book ticket");
            throw new Exception("Something went wrong.");
        }
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public List<Ticket> getAllTicketsByUsername(String username) {
        return ticketRepository.findByUsername(username);
    }

    @Transactional
    public Ticket updateTicket(Long ticketId, Long flightId) throws Exception {
        try {
            Ticket ticket = ticketRepository.findById(ticketId)
                    .orElseThrow(() -> new Exception("Can`t find user by ticketId"));
            Flight flight = flightRepository.findById(flightId)
                    .orElseThrow(() -> new Exception("Can`t find flight by flightId"));
            ticket.setFlightAndStatus(flight.getId(), "BOOKED");
            return ticketRepository.save(ticket);
        } catch (Exception e) {
            log.info("Can`t update ticket: {}", e.getMessage());
            throw new Exception("Can`t update ticket");
        }
    }


    public boolean deleteById(Long id) throws Exception {
        try {
            ticketRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            log.error("Ticket id could not be null, {}", e.getMessage());
            throw new Exception("Ticket id could not be null");
        }
    }
}
