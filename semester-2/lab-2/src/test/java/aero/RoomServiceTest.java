package aero;

import aero.dto.FlightDTO;
import aero.models.Flight;
import aero.repositories.FlightRepository;
import aero.services.FlightService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class RoomServiceTest {

    @MockBean
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Test
    public void getAllFlights() {
        List<Flight> list = new ArrayList<>();
        when(flightRepository.findAll()).thenReturn(list);
        FlightDTO dto = flightService.findAllFlights();
        Assertions.assertEquals(list, dto.getFlights());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    public void findFlightById() throws Exception {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setArrivalCountry("Ukraine");
        flight.setDepartureCountry("Germany");
        flight.setPrice(12345);
        flight.setArrivalTime("01.01.2021:18:30");
        flight.setDepartureTime("01.01.2021:20:30");
        flight.setNumberOfSeats(123);
        flight.setPriceOfBaggage(100);
        flight.setPriceOfPriorityRegister(300);

        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        Assertions.assertEquals(flight, flightService.findByFlightId(anyLong()));
        verify(flightRepository, times(1)).findById(anyLong());
    }
}
