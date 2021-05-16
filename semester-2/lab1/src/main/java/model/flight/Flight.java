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

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate departureTime;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate arrivalTime;

    public Flight(Long id, Integer price, Integer priceOfBaggage, Integer priceOfPriorityRegister,
                  Integer numberOfSeats, String departureCountry, String arrivalCountry,
                  LocalDate departureTime, LocalDate arrivalTime) {
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

    public LocalDate getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDate arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
