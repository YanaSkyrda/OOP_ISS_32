package aero.controllers;

import aero.dto.TicketDTO;
import aero.models.Ticket;
import aero.services.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/ticket")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PostMapping("/ticket")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Ticket> createNewTicket(@RequestBody TicketDTO ticketDTO) {
        try {
            return ResponseEntity.ok(ticketService.createTicket(ticketDTO));
        } catch (Exception e) {
            log.error("Cannot create ticket");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

    }
}
