package dao;

import model.ticket.Ticket;

import java.util.List;

public interface TicketDao extends GenericDao<Ticket> {
    List<Ticket> findTicketsByUser(Long id);
}
