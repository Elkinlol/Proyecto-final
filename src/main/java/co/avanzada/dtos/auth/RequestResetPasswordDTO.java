package co.avanzada.dtos.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record RequestResetPasswordDTO(@NotBlank @Size(min = 6) Long codigoRecuperacion,
                                      @NotBlank
                                      @Min(8)
                                      @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
                                              message = "La contraseña nueva debe contener al menos una mayúscula, una minúscula y un número")
                                      String newPassword) {
}
