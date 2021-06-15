package aero.dto;

import aero.models.Flight;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    @JsonProperty
    private Long id;

    @JsonProperty
    private Integer price;

    @JsonProperty
    private Integer priceOfBaggage;

    @JsonProperty
    private Integer priceOfPriorityRegister;

    @JsonProperty
    private Integer numberOfSeats;

    @JsonProperty
    private String departureCountry;

    @JsonProperty
    private String arrivalCountry;

    @JsonProperty
    private String departureTime;

    @JsonProperty
    private String arrivalTime;
}
