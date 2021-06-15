package aero;

import aero.dto.TicketDTO;
import aero.models.Ticket;
import aero.models.User;
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


    @Autowired
    private TicketService ticketService;


    @Test
    public void getAllTickets() {
        List<Ticket> list = new ArrayList<>();
        when(ticketRepository.findAllByOrderByStatusDesc()).thenReturn(list);
        assertEquals(list, ticketService.getAllTickets());
        verify(ticketRepository, times(1)).findAllByOrderByStatusDesc();
    }

    @Test
    public void getAllTicketsByUser() {
        List<Ticket> list = new ArrayList<>();

        when(ticketRepository.findByUsername("username")).thenReturn(list);

        assertEquals(list, ticketService.getAllTicketsByUsername("username"));

        verify(ticketRepository, times(1)).findByUsername("username");
    }

    @Test
    public void createNewTicketByUser() throws Exception {
        Ticket ticket = new Ticket();
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setStatus("BOOKED");
        ticketDTO.setId(1243L);
        ticketDTO.setBaggagePrice(23456);
        ticketDTO.setFlightPrice(243456);
        ticketDTO.setPriorityRegisterPrice(456);
        ticketDTO.setHavePriorityRegister(Boolean.TRUE);
        ticketDTO.setHaveBaggage(Boolean.TRUE);
        ticketDTO.setSeat("Business");

        aero.models.User user = new User();
        user.setUsername("username");

        ticketDTO.setUsername(user.getUsername());

        when(ticketRepository.save(any())).thenReturn(ticket);

        assertEquals(ticket, ticketService.createTicket(ticketDTO));

        verify(ticketRepository, times(1)).save(any());
    }

    @Test
    public void shouldUpdateReservationById() throws Exception {

        Ticket prevTicket = new Ticket();
        prevTicket.setStatus("PENDING");
        Ticket ticket = new Ticket();
        ticket.setStatus("BOOKED");

        when(ticketRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(ticketRepository.save(any())).thenReturn(ticket);

        ticketService.updateTicket(1L, 3L);

        verify(ticketRepository, times(2)).findById(anyLong());
    }
}
