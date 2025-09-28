package co.avanzada.dtos.auth;

import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

public record RequestResetPasswordDTO(@NotBlank @Size(min=5,max=5) String recuperationCode,
                                      @NotBlank
                                      @Min(8)
                                      @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
                                              message = "La contraseña nueva debe contener al menos una mayúscula, una minúscula y un número")
                                      String newPassword,  @Email @NotBlank String email) {
}
