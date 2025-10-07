package co.avanzada.dtos.auth;

import co.avanzada.dtos.user.UserDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record LoginResponseDTO(
         @Valid UserDTO userDTO,
         @NotBlank String token
) {
}
