package co.avanzada.dtos.user;

import co.avanzada.model.enunms.Rol;
import co.avanzada.model.enunms.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;

public record UserDTO(
        @Email @NotNull String email,
        @NotBlank @Size(max= 100) String fullName,
        @NotBlank @Size(max= 20) String numberPhone,
        @NotNull String birthday,
        @NotBlank String id, @NotNull LocalDate createdAt, @NotNull Set<Rol> rol, @NotNull Status status
        ) {}
