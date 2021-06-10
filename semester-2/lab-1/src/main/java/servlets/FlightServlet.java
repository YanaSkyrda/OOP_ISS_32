package servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.FlightsService;
import util.JsonConverter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "flight", value = "/flight")
public class FlightServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private final FlightsService flightsService = new FlightsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing flight controller");
        try {
            JsonConverter.makeJsonAnswer(flightsService.findAllFlights(), resp);
        } catch (Exception e) {
            logger.error("Something went wrong");
            logger.error(e.getMessage());
            resp.sendError(400, e.getMessage());
        }
    }
}
