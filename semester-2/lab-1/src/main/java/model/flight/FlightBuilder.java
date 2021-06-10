package model.flight;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import util.LocalDateDeserializer;
import util.LocalDateSerializer;

import java.time.LocalDate;

public class FlightBuilder {
    private Long id;

    private Integer price;

    private Integer priceOfBaggage;

    private Integer priceOfPriorityRegister;

    private Integer numberOfSeats;

    private String departureCountry;

    private String arrivalCountry;

    private String departureTime;

    private String arrivalTime;

    public FlightBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public FlightBuilder setPrice(Integer price) {
        this.price = price;
        return this;
    }

    public FlightBuilder setPriceOfBaggage(Integer priceOfBaggage) {
        this.priceOfBaggage = priceOfBaggage;
        return this;
    }

    public FlightBuilder setPriceOfPriorityRegister(Integer priceOfPriorityRegister) {
        this.priceOfPriorityRegister = priceOfPriorityRegister;
        return this;
    }

    public FlightBuilder setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
        return this;
    }

    public FlightBuilder setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
        return this;
    }

    public FlightBuilder setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
        return this;
    }

    public FlightBuilder setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
        return this;
    }

    public FlightBuilder setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public Flight build() {
        return new Flight(id, price, priceOfBaggage, priceOfPriorityRegister, numberOfSeats,
                departureCountry, arrivalCountry, departureTime, arrivalTime);
    }
}
