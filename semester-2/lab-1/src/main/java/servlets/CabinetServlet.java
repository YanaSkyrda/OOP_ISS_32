package servlets;

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

@WebServlet(name = "cabinet", value = "/cabinet")
public class CabinetServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(CabinetServlet.class);
    private final TicketsService ticketsService = new TicketsService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing cabinet get controller");
        try {
            List<Ticket> listOfTickets = ticketsService.findAllTicketsByUser(
                    Long.parseLong(req.getParameter("user")));
            JsonConverter.makeJsonAnswer(listOfTickets, resp);

        } catch (Exception e) {
            logger.info("Tickets can`t be found due to exception: {}", e.getMessage());
            resp.sendError(400);
        }
    }
}
