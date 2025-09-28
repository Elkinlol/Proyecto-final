package co.avanzada.model;



import co.avanzada.model.enunms.Services;
import co.avanzada.model.enunms.Status;
import jakarta.persistence.*;
import lombok.*;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private Adress adress;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Host host;

    @Column(nullable = false)
    private BigDecimal nightlyPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Integer maxGuest;

    @Column(nullable = false)
    private List<Services> services;

    @OneToMany
    private List<Review> reviews;

    @ManyToOne
    private Promotion promotion;
}
