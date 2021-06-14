package aero.dto;

import aero.models.Flight;
import lombok.Data;

import java.util.List;

@Data
public class FlightDTO {
    public List<Flight> flights;

    public FlightDTO(List<Flight> flights) {
        this.flights = flights;
    }
}
