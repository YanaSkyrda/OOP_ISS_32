import dao.FactoryDao;
import dao.TicketDao;
import model.ticket.Ticket;
import model.ticket.TicketBuilder;
import org.junit.Before;
import org.junit.Test;
import service.TicketsService;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TicketsServiceTest {
    private TicketsService ticketsService;
    private final FactoryDao factoryDao = mock(FactoryDao.class);
    private final TicketDao ticketDao = mock(TicketDao.class);

    Ticket ticket;

    private final String USERNAME = "username";

    @Before
    public void initTicket() {
        ticketsService = new TicketsService();
        ticketsService.setFactoryDao(factoryDao);
        ticket = new TicketBuilder()
                .setId(1234L)
                .setHaveBaggage(Boolean.TRUE)
                .setBaggagePrice(100)
                .setHavePriorityRegister(Boolean.FALSE)
                .setPriorityRegisterPrice(0)
                .setFlightPrice(2300)
                .setSeat("Standard")
                .setStatus("BOOKED")
                .setUsername(USERNAME)
                .build();

    }

    @Test
    public void shouldCreateNewTicket() throws Exception {
        when(factoryDao.createTicketDao()).thenReturn(ticketDao);
        when(ticketDao.create(ticket)).thenReturn(Optional.of(ticket));
        ticketsService.createNewTicket(ticket);
        verify(factoryDao, times(1)).createTicketDao();
        verify(ticketDao, times(1)).create(ticket);
    }

    @Test
    public void shouldFindAllTicketsByUser() throws Exception {
        when(factoryDao.createTicketDao()).thenReturn(ticketDao);
        when(ticketDao.findTicketsByUser(USERNAME)).thenReturn(new ArrayList<>());
        ticketsService.findAllTicketsByUser(USERNAME);
        verify(factoryDao, times(1)).createTicketDao();
        verify(ticketDao, times(1)).findTicketsByUser(USERNAME);
    }

    @Test
    public void shouldFindAllTickets() throws Exception {
        when(factoryDao.createTicketDao()).thenReturn(ticketDao);
        when(ticketDao.findAll()).thenReturn(new ArrayList<>());
        ticketsService.findAllTickets();
        verify(factoryDao, times(1)).createTicketDao();
        verify(ticketDao, times(1)).findAll();
    }


}
