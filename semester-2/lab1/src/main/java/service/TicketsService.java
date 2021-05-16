package service;

import dao.FactoryDao;
import dao.TicketDao;
import model.ticket.Ticket;

import java.util.List;

public class TicketsService {

    private FactoryDao factoryDao = FactoryDao.getInstance();

    public Ticket createNewTicket(Ticket ticket) throws Exception {
        try (TicketDao ticketDao = factoryDao.createTicketDao()) {
            return ticketDao.create(ticket)
                    .orElseThrow(() -> new Exception("Error while creating ticket"));
        }
    }

    public List<Ticket> findAllTicketsByUser(Long id) throws Exception {
        try (TicketDao ticketDao = factoryDao.createTicketDao()) {
            return ticketDao.findTicketsByUser(id);
        }
    }

    public List<Ticket> findAllTickets() throws Exception {
        try (TicketDao ticketDao = factoryDao.createTicketDao()) {
            return ticketDao.findAll();
        }
    }

    public Ticket updateTicket(Ticket ticket) throws Exception {
        try (TicketDao ticketDao = factoryDao.createTicketDao()) {
            return ticketDao.update(ticket)
                    .orElseThrow(() -> new Exception("Error while updating ticket"));
        }
    }

    public void setFactoryDao(FactoryDao factoryDao) {
        this.factoryDao = factoryDao;
    }
}
