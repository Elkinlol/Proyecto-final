package co.avanzada.dtos.user;

import jakarta.validation.constraints.*;

public record LoginUserDTO(@Email  @NotBlank Email email,  @NotBlank @Min(8) String password) {
}
