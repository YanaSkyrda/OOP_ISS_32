package service;

import dao.FactoryDao;
import dao.FlightDao;
import model.flight.Flight;

import java.util.List;

public class FlightsService {

    private FactoryDao factoryDao = FactoryDao.getInstance();

    public List<Flight> findAllFlights() throws Exception {
        try (FlightDao flightDao = factoryDao.createFlightDao()) {
            return flightDao.findAll();
        }
    }

    public void setFactoryDao(FactoryDao factoryDao) {
        this.factoryDao = factoryDao;
    }
}
