package co.avanzada.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
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

    @Column(nullable = false)
    private String listingId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;


    @Column(nullable = false)
    private ReservationStatus reservationsStatus;

    @Column(nullable = false, scale = 2)
    private BigDecimal price;


}
