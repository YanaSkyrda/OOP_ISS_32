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
                .setPriceOfBaggage(resultSet.getInt("price_baggage"))
                .setPriceOfPriorityRegister(resultSet.getInt("price_priority_register"))
                .setNumberOfSeats(resultSet.getInt("number_of_seats"))
                .setDepartureCountry(resultSet.getString("departure_country"))
                .setArrivalCountry(resultSet.getString("arrival_country"))
                .setDepartureTime(resultSet.getDate("departure_time").toLocalDate())
                .setArrivalTime(resultSet.getDate("arrival_time").toLocalDate())
                .build();
    }

}

