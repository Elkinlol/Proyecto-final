package co.avanzada.model;



import co.avanzada.model.enunms.Services;
import co.avanzada.model.enunms.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private String id;

    @Column(nullable = false, length = 70)
    private  String title;

    @Column(nullable = false, length = 500)
    private  String description;

    @Column(nullable = false, length = 500)
    private  String url;

    @ElementCollection @Size(min = 1, max= 10)
    private List<String> image;

    @Column(nullable = false)
    private LocalDateTime creationDate;

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

    @Enumerated(EnumType.STRING)
    private List<Services> services;

    @OneToMany
    private List<Review> reviews;

    @ManyToOne
    private Promotion promotion;
}
