package co.avanzada.dtos.auth;

import co.avanzada.dtos.user.UserDTO;
import jakarta.validation.Valid;

public record LoginResponseDTO(
         @Valid UserDTO userDTO
) {
}
