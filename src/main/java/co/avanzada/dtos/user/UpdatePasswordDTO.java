package co.avanzada.dtos.user;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter

public class UpdatePasswordDTO {
     @NotBlank @Length(min = 8)  private String oldPassword;


    @NotBlank
    @Length(min = 8)
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*$",
            message = "La contraseña nueva debe contener al menos una mayúscula, una minúscula y un número"
    )
    private String newPassword;

}
