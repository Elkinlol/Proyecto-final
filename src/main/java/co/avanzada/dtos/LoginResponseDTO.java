package co.avanzada.dtos;

import co.avanzada.dtos.user.UserDTO;
import jakarta.validation.Valid;

public record LoginResponseDTO(
         @Valid UserDTO userDTO
) {
}
