package aero.mapper;

import aero.dto.TicketDTO;
import aero.models.Ticket;

public interface TicketMapper {
    default Ticket toEntity(TicketDTO ticketDTO) {
        return Ticket.builder()
                .id(ticketDTO.getId())
                .haveBaggage(ticketDTO.getHaveBaggage())
                .baggagePrice(ticketDTO.getBaggagePrice())
                .havePriorityRegister(ticketDTO.getHavePriorityRegister())
                .priorityRegisterPrice(ticketDTO.getPriorityRegisterPrice())
                .flightPrice(ticketDTO.getFlightPrice())
                .flightId(ticketDTO.getFlightId())
                .seat(ticketDTO.getSeat())
                .status(ticketDTO.getStatus())
                .username(ticketDTO.getUsername())
                .build();
    }
}
