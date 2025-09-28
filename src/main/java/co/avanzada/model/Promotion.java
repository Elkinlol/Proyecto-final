package co.avanzada.model;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "Promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, scale = 2)
    private BigDecimal porcentaje;

    @Column(nullable = false)
    private LocalDate createdAt;

    @OneToOne
    @JoinColumn(nullable = false)
    private  Listing listing;

}
