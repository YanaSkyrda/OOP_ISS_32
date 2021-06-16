package aero;

import aero.dto.TicketDTO;
import aero.mapper.TicketMap;
import aero.mapper.TicketMapper;
import aero.models.Flight;
import aero.models.Ticket;
import aero.models.User;
import aero.repositories.FlightRepository;
import aero.repositories.TicketRepository;
import aero.services.TicketService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketServiceTest {

    @MockBean
    private TicketRepository ticketRepository;

    @MockBean
    private FlightRepository flightRepository;

    @Autowired
    private TicketService ticketService;

    private final String USERNAME = "username";

    @Test
    public void getAllTickets() {
        List<Ticket> list = new ArrayList<>();

        when(ticketRepository.findAll()).thenReturn(list);

        assertEquals(list, ticketService.getAllTickets());

        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    public void getAllTicketsByUser() {
        List<Ticket> list = new ArrayList<>();

        when(ticketRepository.findByUsername(USERNAME)).thenReturn(list);

        assertEquals(list, ticketService.getAllTicketsByUsername(USERNAME));

        verify(ticketRepository, times(1)).findByUsername(USERNAME);
    }

    @Test
    public void createNewTicketByUser() throws Exception {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setStatus("BOOKED");
        ticketDTO.setId(1243L);
        ticketDTO.setBaggagePrice(23456);
        ticketDTO.setFlightPrice(243456);
        ticketDTO.setPriorityRegisterPrice(456);
        ticketDTO.setHavePriorityRegister(Boolean.TRUE);
        ticketDTO.setHaveBaggage(Boolean.TRUE);
        ticketDTO.setSeat("Business");

        Ticket ticket = new TicketMap().toEntity(ticketDTO);

        aero.models.User user = new User();
        user.setUsername("username");

        ticketDTO.setUsername(user.getUsername());

        when(ticketRepository.save(any())).thenReturn(ticket);

        assertEquals(ticket, ticketService.createTicket(ticketDTO));

        verify(ticketRepository, times(1)).save(any());
    }

    @Test
    public void shouldUpdateTicketById() throws Exception {

        Ticket prevTicket = new Ticket();
        prevTicket.setStatus("PENDING");
        Ticket ticket = new Ticket();
        ticket.setStatus("BOOKED");

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(prevTicket));
        when(flightRepository.findById(3L)).thenReturn(Optional.of(Flight.builder().build()));
        when(ticketRepository.save(prevTicket)).thenReturn(ticket);

        Ticket res = ticketService.updateTicket(1L, 3L);

        verify(ticketRepository, times(1)).findById(1L);
        verify(flightRepository, times(1)).findById(3L);

        assertEquals(ticket.getStatus(), res.getStatus());
    }
}
