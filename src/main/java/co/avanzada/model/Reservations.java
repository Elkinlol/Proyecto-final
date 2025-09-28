package co.avanzada.model;

import co.avanzada.model.enunms.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name= "Reservations")
@Entity
public class Reservations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    @Column(nullable = false)
    private int guestCount;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Listing listings;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;


    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationsStatus;

    @Column(nullable = false, scale = 2)
    private BigDecimal price;


}
