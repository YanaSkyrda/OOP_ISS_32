package aero.models;

import lombok.*;

import javax.persistence.*;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "have_baggage")
    private Boolean haveBaggage;

    @Basic
    @Column(name = "baggage_price")
    private Integer baggagePrice;

    @Basic
    @Column(name = "have_priority_register")
    private Boolean havePriorityRegister;

    @Basic
    @Column(name = "priority_register_price")
    private Integer priorityRegisterPrice;

    @Basic
    @Column(name = "flight_id")
    private Long flightId;

    @Basic
    @Column(name = "flight_price")
    private Integer flightPrice;

    @Basic
    @Column(name = "seat")
    private String seat;

    @Basic
    @Column(name = "username")
    private String username;

    @Basic
    @Column(name = "status")
    private String status;

    public void setFlightAndStatus(Long flightId, String status) {
        setFlightId(flightId);
        setStatus(status);
    }
}
