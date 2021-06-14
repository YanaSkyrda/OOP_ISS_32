package aero.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TicketDTO {
    @JsonProperty
    private Long id;

    @JsonProperty
    private Boolean haveBaggage;

    @JsonProperty
    private Integer baggagePrice;

    @JsonProperty
    private Boolean havePriorityRegister;

    @JsonProperty
    private Integer priorityRegisterPrice;

    @JsonProperty
    private Long flightId;

    @JsonProperty
    private Integer flightPrice;

    @JsonProperty
    private String seat;

    @JsonProperty
    private String username;

    @JsonProperty
    private String status;
}
