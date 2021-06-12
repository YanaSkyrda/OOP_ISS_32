package model.ticket;

import model.flight.Flight;
import model.user.User;

public class Ticket {
    private Long id;

    private Boolean haveBaggage;

    private Integer baggagePrice;

    private Boolean havePriorityRegister;

    private Integer priorityRegisterPrice;

    private Long flightId;

    private Integer flightPrice;

    private String seat;

    private String username;

    private String status;

    public Ticket() {
    }

    public Ticket(Long id, Boolean haveBaggage, Integer baggagePrice,
                  Boolean havePriorityRegister, Integer priorityRegisterPrice,
                  Long flightId, Integer flightPrice, String seat,
                  String username, String status) {
        this.id = id;
        this.haveBaggage = haveBaggage;
        this.baggagePrice = baggagePrice;
        this.havePriorityRegister = havePriorityRegister;
        this.priorityRegisterPrice = priorityRegisterPrice;
        this.flightId = flightId;
        this.flightPrice = flightPrice;
        this.seat = seat;
        this.username = username;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getHaveBaggage() {
        return haveBaggage;
    }

    public void setHaveBaggage(Boolean haveBaggage) {
        this.haveBaggage = haveBaggage;
    }

    public Integer getBaggagePrice() {
        return baggagePrice;
    }

    public void setBaggagePrice(Integer baggagePrice) {
        this.baggagePrice = baggagePrice;
    }

    public Boolean getHavePriorityRegister() {
        return havePriorityRegister;
    }

    public void setHavePriorityRegister(Boolean havePriorityRegister) {
        this.havePriorityRegister = havePriorityRegister;
    }

    public Integer getPriorityRegisterPrice() {
        return priorityRegisterPrice;
    }

    public void setPriorityRegisterPrice(Integer priorityRegisterPrice) {
        this.priorityRegisterPrice = priorityRegisterPrice;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public Integer getFlightPrice() {
        return flightPrice;
    }

    public void setFlightPrice(Integer flightPrice) {
        this.flightPrice = flightPrice;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
