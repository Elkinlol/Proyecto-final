package co.avanzada.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordDTO(@Email @NotNull String email) {
}
