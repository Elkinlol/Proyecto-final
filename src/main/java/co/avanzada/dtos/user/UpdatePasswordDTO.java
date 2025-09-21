package co.avanzada.dtos.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class UpdatePasswordDTO {
     @NotBlank @Min(8)  private String oldPassword;


    @NotBlank
    @Min(8)
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "La contraseña nueva debe contener al menos una mayúscula, una minúscula y un número"
    )
    private String newPassword;

}
