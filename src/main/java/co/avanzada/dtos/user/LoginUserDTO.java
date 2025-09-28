package co.avanzada.dtos.user;

import jakarta.validation.constraints.*;

public record LoginUserDTO(@Email  @NotBlank String email,  @NotBlank @Size(min = 8) String password) {
}
