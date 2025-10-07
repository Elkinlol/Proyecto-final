package co.avanzada.dtos.user;

import jakarta.validation.constraints.NotBlank;

public record UpdateProfileHostDTO(@NotBlank String aboutMe, @NotBlank String documents) {
}
