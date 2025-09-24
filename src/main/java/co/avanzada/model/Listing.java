package co.avanzada.model;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name= "Listings")
@Entity

public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 70)
    private  String title;

    @Column(nullable = false, length = 500)
    private  String description;

    @Column(nullable = false, length = 500)
    private  String url;

    @Column(nullable = false)
    private List<String> image;

    @Column(nullable = false)
    private LocalDate creationDate;

    @OneToOne
    @Column(nullable = false)
    private Adress adress;

    @OneToMany
    @Column(nullable = false)
    private Host host;

    @Column(nullable = false)
    private BigDecimal nightlyPrice;

    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private Integer maxGuest;

    @Column(nullable = false)
    private List<Services> services;

    @OneToMany(mappedBy = "place") //
    private List<Review> reviews;
}
