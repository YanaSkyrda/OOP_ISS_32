package util;

import model.flight.FlightBuilder;
import model.ticket.Ticket;
import model.ticket.TicketBuilder;

import javax.servlet.http.HttpServletRequest;

public class Util {

    public static Ticket buildAdminTicket(HttpServletRequest request) {

        return new TicketBuilder()
                .setFlightId(new FlightBuilder()
                        .setId(Long.parseLong(request.getParameter("flight_id")))
                        .build())
                .setId(Long.parseLong(request.getParameter("id")))
                .build();
    }
}
