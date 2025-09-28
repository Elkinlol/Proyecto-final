package co.avanzada.dtos.user;

import co.avanzada.model.enunms.Rol;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.Set;


public record CreateUserDTO(@Email @NotBlank String email,
                            @NotBlank
                            @Size(min=8)
                            @Pattern(
                            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
                            message = "La contraseña nueva debe contener al menos una mayúscula, una minúscula y un número")
                            String password,
                            @NotBlank @Size(min=10, max = 100) String fullName,
                            @NotBlank @Size(min=8) String numberPhone, @Valid @NotNull LocalDate birthday,@NotNull Set<Rol> rol) {
}
