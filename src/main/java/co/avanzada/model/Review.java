package co.avanzada.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name= "Reviews")
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5, nullable = false)
    private int rating;

    @ManyToOne()
    @JoinColumn( nullable = false)
    private Listing listing;

    @Column(length = 500, nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn( nullable = false)
    private User user;

    @Column( nullable = false)
    private LocalDate createdAt;

    @OneToMany
    @JoinColumn( nullable = false)
    private Reply replyAt;
}
