package aero.repositories;

import aero.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUsername(String username);

    List<Ticket> findAllByOrderByStatusDesc();
}
