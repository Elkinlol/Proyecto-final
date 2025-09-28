package co.avanzada.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= "codigosRecuperacion")
public class PasswordResetCode {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false, length = 5)
    private String code;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
}
