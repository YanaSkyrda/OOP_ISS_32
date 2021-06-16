package aero;

import aero.dto.FlightDTO;
import aero.dto.TicketDTO;
import aero.mapper.FlightMapper;
import aero.mapper.TicketMap;
import aero.models.Flight;
import aero.models.Ticket;
import aero.models.User;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FlightServiceTest {

    @MockBean
    private FlightRepository flightRepository;

    @Autowired
    private FlightService flightService;

    @Test
    public void getAllFlights() {
        List<Flight> list = new ArrayList<>();
        when(flightRepository.findAll()).thenReturn(list);
        List<FlightDTO> allFlights = flightService.findAllFlights();
        Assertions.assertTrue(list.containsAll(allFlights.stream().map(FlightMapper::toEntity).collect(Collectors.toList())));
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    public void findFlightById() throws Exception {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setArrivalCountry("Ukraine");
        flight.setDepartureCountry("Germany");
        flight.setPrice(12345);
        flight.setArrivalTime("01/01/2021:18:30");
        flight.setDepartureTime("01/01/2021:20:30");
        flight.setNumberOfSeats(123);
        flight.setPriceOfBaggage(100);
        flight.setPriceOfPriorityRegister(300);

        when(flightRepository.findById(anyLong())).thenReturn(Optional.of(flight));
        Assertions.assertEquals(flight, flightService.findByFlightId(anyLong()));
        verify(flightRepository, times(1)).findById(anyLong());
    }

    @Test
    public void shouldCreateFlight() throws Exception {
        Flight flight = new Flight();
        flight.setId(1L);
        flight.setArrivalCountry("Ukraine");
        flight.setDepartureCountry("Germany");
        flight.setPrice(12345);
        flight.setArrivalTime("01/01/2021:18:30");
        flight.setDepartureTime("01/01/2021:20:30");
        flight.setNumberOfSeats(123);
        flight.setPriceOfBaggage(100);
        flight.setPriceOfPriorityRegister(300);

        when(flightRepository.save(any())).thenReturn(flight);

        assertEquals(flight, flightService.createFlight(FlightMapper.toDTO(flight)));

        verify(flightRepository, times(1)).save(any());
    }

    @Test
    public void shouldThrowExceptionOnCreateFlight() {

        Flight flight = new Flight();
        flight.setId(1L);
        flight.setArrivalCountry("Ukraine");
        flight.setDepartureCountry("Germany");
        flight.setPrice(12345);
        flight.setArrivalTime("01/01/2021:18:30");
        flight.setDepartureTime("01/01/2021:20:30");
        flight.setNumberOfSeats(123);
        flight.setPriceOfBaggage(100);
        flight.setPriceOfPriorityRegister(300);

        when(flightRepository.save(any())).thenThrow(new IllegalArgumentException());

        assertThrows(Exception.class,
                () -> flightService.createFlight(FlightMapper.toDTO(flight)),
                "Something went wrong.");

        verify(flightRepository, times(1)).save(any());
    }
}
