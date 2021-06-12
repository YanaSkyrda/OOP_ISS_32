package dao.mapper;

import model.ticket.Ticket;
import model.ticket.TicketBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketMapper {

    public Ticket getTicketFromResultSet(ResultSet resultSet) throws SQLException {

        return new TicketBuilder()
                .setId(resultSet.getLong("id"))
                .setHaveBaggage(resultSet.getBoolean("have_baggage"))
                .setBaggagePrice(resultSet.getInt("baggage_price"))
                .setHavePriorityRegister(resultSet.getBoolean("have_priority_register"))
                .setPriorityRegisterPrice(resultSet.getInt("priority_register_price"))
                .setFlightPrice(resultSet.getInt("flight_price"))
                .setSeat(resultSet.getString("seat"))
                .setStatus(resultSet.getString("status"))
                .setUsername(resultSet.getString("username"))
                .build();
    }


}
