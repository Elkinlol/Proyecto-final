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
    private String id = UUID.randomUUID().toString();

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

    @Enumerated(EnumType.STRING)
    private Rol rol;


    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = true)
    private String profilePhoto;

    @OneToMany
    @Column(nullable=false)
    private List<Reservations> reservations;

    @OneToMany
    private List<Review> reviews;

    @Embedded
    private Host host;

    @Column(nullable=false)
    private Boolean isHost;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PasswordResetCode> resetCodes = new ArrayList<>();

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Listing> listings;

    @OneToMany
    private List<Reply> replies;
}

