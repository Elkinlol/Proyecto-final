package co.avanzada.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Host {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private  String aboutMe;

    @Column(nullable = false, length = 500)
    private  String documents;

    @OneToOne
    @JoinColumn(nullable=false)
    private User user;
}
