package aero.mapper;

import aero.dto.FlightDTO;
import aero.dto.TicketDTO;
import aero.models.Flight;
import aero.models.Ticket;

import javax.persistence.*;

public class FlightMapper {

    public static FlightDTO toDTO(Flight flight) {
        return FlightDTO.builder()
                .id(flight.getId())
                .price(flight.getPrice())
                .priceOfBaggage(flight.getPriceOfBaggage())
                .priceOfPriorityRegister(flight.getPriceOfPriorityRegister())
                .numberOfSeats(flight.getNumberOfSeats())
                .departureCountry(flight.getDepartureCountry())
                .arrivalCountry(flight.getArrivalCountry())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .build();
    }
}
