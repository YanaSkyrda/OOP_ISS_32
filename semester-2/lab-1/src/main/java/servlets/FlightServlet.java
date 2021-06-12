package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.flight.Flight;
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
    static final Logger logger = LogManager.getLogger(FlightServlet.class);
    private final FlightsService flightsService = new FlightsService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing flight controller");
        try {
            JsonConverter.makeJsonAnswer(flightsService.findAllFlights(), resp);
        } catch (Exception e) {
            logger.error("Something went wrong ", e);
            resp.sendError(400, e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("Executing flight post controller");
        try {
            String s = JsonConverter.jsonBodyFromRequest(req, resp).get("flight").toString();
            Flight flight = flightsService.createNewFlight(new ObjectMapper()
                    .readValue(s, Flight.class));
            JsonConverter.makeJsonAnswer(flight, resp);
        } catch (Exception e) {
            logger.error("Can`t create flight due to exception ", e);
            resp.sendError(400, e.getMessage());
        }
    }
}
