package dao.impl;

import dao.FlightDao;
import dao.mapper.FlightMapper;
import model.flight.Flight;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FlightDaoImpl implements FlightDao {
    private final Logger logger = LogManager.getLogger(FlightDaoImpl.class);
    private final Connection connection;
    private final FlightMapper flightMapper = new FlightMapper();

    ResourceBundle bundle = ResourceBundle.getBundle("sql");

    public FlightDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Flight> create(Flight entity) {
        return Optional.empty();
    }

    @Override
    public Flight findById(long id) {
        Flight flight = new Flight();
        try {
            PreparedStatement firstStatement = connection.prepareStatement("SELECT * FROM flights WHERE id=?");
            firstStatement.setLong(1, id);
            ResultSet resultSet = firstStatement.executeQuery();

            if (resultSet.next()) {
                flight.setId(resultSet.getLong("id"));
                return flight;
            }
        } catch (SQLException e) {
            logger.error("Flight can`t be found: {}", e.getMessage());
        }
        return flight;
    }

    @Override
    public List<Flight> findAll() {
        ResultSet set;
        List<Flight> flights = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(bundle.getString("flight.find_all"));
            set = statement.executeQuery();
            while (set.next()) {
                flights.add(flightMapper.getRoomFromResultSet(set));
            }
        } catch (SQLException e) {
            logger.warn("Flights can`t be found: {}", e.getMessage());
        }
        return flights;
    }

    @Override
    public Optional<Flight> update(Flight entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
