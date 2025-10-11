package co.avanzada.model;

import co.avanzada.model.enunms.ReservationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name= "Reservations")
@Entity
public class Reservations {
    @Id
    private String  id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private LocalDateTime checkIn;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime checkOut;

    @Column(nullable = false)
    private Integer guestCount;

    @Column(nullable = true)
    private String discountCode;

    @ManyToOne
    @JoinColumn(nullable = false, name = "listing_id", referencedColumnName = "id")
    private Listing listings;

    @ManyToOne
    @JoinColumn(nullable = false, name= "user_id", referencedColumnName = "id")
    private User user;


    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationsStatus;

    @Column(nullable = false, scale = 2)
    private BigDecimal price;


}
