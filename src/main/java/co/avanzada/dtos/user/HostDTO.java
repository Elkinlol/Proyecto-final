package co.avanzada.dtos.user;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record HostDTO(@Valid UserDTO userDTO, @NotBlank String aboutMe, @NotBlank String documents) {
}
