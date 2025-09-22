package co.avanzada.dtos.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.Date;

public record CreateUserDTO(@Email @NotBlank String email,
                            @NotBlank
                            @Min(8)
                            @Pattern(
                            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
                            message = "La contraseña nueva debe contener al menos una mayúscula, una minúscula y un número")
                            String password,
                            @NotBlank @Size(min=10, max = 100) String fullName,
                            @NotBlank @Size(min=8) String numberPhone, @Valid @NotBlank @Size(min=8)Date birthday) {
}
