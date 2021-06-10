package dao.mapper;

import model.flight.Flight;
import model.flight.FlightBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightMapper {

    public Flight getRoomFromResultSet(ResultSet resultSet) throws SQLException {
        return new FlightBuilder()
                .setId(resultSet.getLong("id"))
                .setPrice(resultSet.getInt("price"))
                .setPriceOfBaggage(resultSet.getInt("price_of_baggage"))
                .setPriceOfPriorityRegister(resultSet.getInt("price_of_priority_register"))
                .setNumberOfSeats(resultSet.getInt("number_of_seats"))
                .setDepartureCountry(resultSet.getString("departure_country"))
                .setArrivalCountry(resultSet.getString("arrival_country"))
                .setDepartureTime(resultSet.getString("departure_time"))
                .setArrivalTime(resultSet.getString("arrival_time"))
                .build();
    }

}

