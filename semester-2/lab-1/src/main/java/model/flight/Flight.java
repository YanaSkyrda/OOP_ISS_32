package model.flight;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import util.LocalDateDeserializer;
import util.LocalDateSerializer;

import java.time.LocalDate;

public class Flight {
    private Long id;

    private Integer price;

    private Integer priceOfBaggage;

    private Integer priceOfPriorityRegister;

    private Integer numberOfSeats;

    private String departureCountry;

    private String arrivalCountry;

    private String departureTime;

    private String arrivalTime;

    public Flight(Long id, Integer price, Integer priceOfBaggage, Integer priceOfPriorityRegister,
                  Integer numberOfSeats, String departureCountry, String arrivalCountry,
                  String departureTime, String arrivalTime) {
        this.id = id;
        this.price = price;
        this.priceOfBaggage = priceOfBaggage;
        this.priceOfPriorityRegister = priceOfPriorityRegister;
        this.numberOfSeats = numberOfSeats;
        this.departureCountry = departureCountry;
        this.arrivalCountry = arrivalCountry;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPriceOfBaggage() {
        return priceOfBaggage;
    }

    public void setPriceOfBaggage(Integer priceOfBaggage) {
        this.priceOfBaggage = priceOfBaggage;
    }

    public Integer getPriceOfPriorityRegister() {
        return priceOfPriorityRegister;
    }

    public void setPriceOfPriorityRegister(Integer priceOfPriorityRegister) {
        this.priceOfPriorityRegister = priceOfPriorityRegister;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public String getArrivalCountry() {
        return arrivalCountry;
    }

    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
