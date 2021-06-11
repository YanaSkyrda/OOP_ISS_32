package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.TicketsService;
import util.JsonConverter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ticket", value = "/ticket")
public class TicketServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(TicketServlet.class);
    private final TicketsService ticketsService = new TicketsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing ticket get controller");
        try {
            List<Ticket> list = ticketsService.findAllTickets();
            JsonConverter.makeJsonAnswer(list, resp);
        } catch (Exception e) {
            logger.error("Can`t receive tickets: {}", e.getMessage());
            resp.sendError(400);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing ticket post controller");
        try {
            String s = JsonConverter.jsonBodyFromRequest(req, resp).get("ticket").toString();
            Ticket ticket = ticketsService.createNewTicket(new ObjectMapper()
                    .readValue(s, Ticket.class));
            JsonConverter.makeJsonAnswer(ticket, resp);
        } catch (Exception e) {
            logger.error("Can`t create ticket due to exception: {}", e.getMessage());
            resp.sendError(400, "Something went wrong");
        }
    }
}
