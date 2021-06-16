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
public class Flight {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic
    @Column(name = "price")
    private Integer price;

    @Basic
    @Column(name = "price_of_baggage")
    private Integer priceOfBaggage;

    @Basic
    @Column(name = "price_of_priority_register")
    private Integer priceOfPriorityRegister;

    @Basic
    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Basic
    @Column(name = "departure_country")
    private String departureCountry;

    @Basic
    @Column(name = "arrival_country")
    private String arrivalCountry;

    @Basic
    @Column(name = "departure_time")
    private String departureTime;

    @Basic
    @Column(name = "arrival_time")
    private String arrivalTime;
}
