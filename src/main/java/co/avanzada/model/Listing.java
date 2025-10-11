package co.avanzada.model;



import co.avanzada.model.enunms.Services;
import co.avanzada.model.enunms.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name= "Listings")
@Entity

public class Listing {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 70)
    private  String title;

    @Column(nullable = false, length = 500)
    private  String description;

    @Column(nullable = false)
    private float averageRating = 0;

    @ElementCollection @Size(max= 10)
    private List<String> image;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "adress_id", referencedColumnName = "id", nullable = false)
    private Adress adress;

    @ManyToOne
    @JoinColumn(name = "host_id", referencedColumnName = "id", nullable = false)
    private User host;

    @Column(nullable = false)
    private BigDecimal nightlyPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Integer maxGuest;

    @ElementCollection(targetClass = Services.class)
    @Enumerated(EnumType.STRING)
    private List<Services> services;

    @OneToMany
    private List<Review> reviews;

    @ManyToOne
    private Promotion promotion;
}
