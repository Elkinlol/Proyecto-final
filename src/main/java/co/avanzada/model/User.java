package co.avanzada.model;

import co.avanzada.model.enunms.Rol;
import co.avanzada.model.enunms.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")

public class User {
    @Id
    private String id;

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

    @ElementCollection(targetClass = Rol.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Rol> rol;


    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany
    @Column(nullable=false)
    private List<Reservations> reservations;

    @OneToMany
    private List<Review> reviews;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Host hostProfile;

    @Column(nullable=false)
    private Boolean isHost;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordResetCode> resetCodes = new ArrayList<>();

    @OneToMany
    private List<Listing> listings;
}

