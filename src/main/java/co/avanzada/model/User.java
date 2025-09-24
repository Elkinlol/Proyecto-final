package co.avanzada.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Users")

public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length = 100)
    private String fullName;

    @Email @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=false, length = 200)
    private String password;

    @Column(nullable=false, length = 200)
    private String numberPhone;

    @Column(nullable=false)
    private LocalDateTime createdAt;

    @Column(nullable=false)
    private LocalDate birthday;

    @ElementCollection
    @Column(nullable=false)
    private Set<Rol> rol;


    @Column(nullable=false)
    private Status status;

    @OneToMany
    @Column(nullable=false)
    private List<Reservations> reservations;

    @OneToMany
    @Column(nullable=false)
    private List<Review> reviews;

    //private Boolean isHost; Como funcionaria? preguntar
    //Deberia tener tambien una lista de alojamientos?
}

