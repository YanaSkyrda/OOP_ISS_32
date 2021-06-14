import dao.FactoryDao;
import dao.FlightDao;
import org.junit.Before;
import org.junit.Test;
import service.FlightsService;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class FlightServiceTest {
    private FlightsService flightService;
    private final FactoryDao factoryDao = mock(FactoryDao.class);
    private final FlightDao flightDao = mock(FlightDao.class);

    @Before
    public void init() {
        flightService = new FlightsService();
        flightService.setFactoryDao(factoryDao);
    }

    @Test
    public void shouldGetAllFlights() throws Exception {
        when(factoryDao.createFlightDao()).thenReturn(flightDao);
        when(flightDao.findAll()).thenReturn(new ArrayList<>());
        flightService.findAllFlights();
        verify(factoryDao, times(1)).createFlightDao();
        verify(flightDao, times(1)).findAll();
    }
}
