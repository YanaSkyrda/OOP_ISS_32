package model.ticket;

import model.flight.Flight;
import model.user.User;

public class TicketBuilder {
    private Long id;

    private Boolean haveBaggage;

    private Integer baggagePrice;

    private Boolean havePriorityRegister;

    private Integer priorityRegisterPrice;

    private Flight flightId;

    private Integer flightPrice;

    private String seat;

    private User userByUserId;

    private String status;

    public TicketBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public TicketBuilder setHaveBaggage(Boolean haveBaggage) {
        this.haveBaggage = haveBaggage;
        return this;
    }

    public TicketBuilder setBaggagePrice(Integer baggagePrice) {
        this.baggagePrice = baggagePrice;
        return this;
    }

    public TicketBuilder setHavePriorityRegister(Boolean havePriorityRegister) {
        this.havePriorityRegister = havePriorityRegister;
        return this;
    }

    public TicketBuilder setPriorityRegisterPrice(Integer priorityRegisterPrice) {
        this.priorityRegisterPrice = priorityRegisterPrice;
        return this;
    }

    public TicketBuilder setFlightId(Flight flightId) {
        this.flightId = flightId;
        return this;
    }

    public TicketBuilder setFlightPrice(Integer flightPrice) {
        this.flightPrice = flightPrice;
        return this;
    }

    public TicketBuilder setSeat(String seat) {
        this.seat = seat;
        return this;
    }

    public TicketBuilder setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
        return this;
    }

    public TicketBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public Ticket build() {
        return new Ticket(id, haveBaggage, baggagePrice, havePriorityRegister, priorityRegisterPrice,
                flightId, flightPrice, seat, userByUserId, status);
    }
}
