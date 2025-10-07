package co.avanzada.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Adress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String adress;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String latitud;

    @Column(nullable = false)
    private String longitud;

    @OneToOne
    @JoinColumn(nullable = false)
    private Listing listing;



}
