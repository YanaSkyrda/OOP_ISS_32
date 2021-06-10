package servlets;

import model.ticket.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.TicketsService;
import util.JsonConverter;
import util.Util;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "admin", value = "/admin")
public class AdminServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final TicketsService ticketsService = new TicketsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing admin post controller");
        try {
            Ticket ticket = ticketsService.updateTicket(Util.buildAdminTicket(req));
            JsonConverter.makeJsonAnswer(ticket, resp);
        } catch (Exception e) {
            logger.error("Can`t update ticket due to exception:{}", e.getMessage());
            resp.sendError(400);
        }
    }
}
