package aero.controllers;

import aero.models.Ticket;
import aero.services.TicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Controller
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class CabinetController {

    private final TicketService ticketService;

    public CabinetController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping("/cabinet")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity getAllTicketsByUsername(@RequestBody String username) {
        List<Ticket> allTicketsByUsername = ticketService.getAllTicketsByUsername(username);
        return ResponseEntity.ok(allTicketsByUsername);
    }
}
