package co.avanzada.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity

public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=500)
    private String message;

    @Column(nullable=false)
    private LocalDateTime createdAt;

    @OneToOne
    private Review review;

    @ManyToOne
    @JoinColumn( name = "host_id", referencedColumnName = "id", nullable = false)
    private User host;

}
