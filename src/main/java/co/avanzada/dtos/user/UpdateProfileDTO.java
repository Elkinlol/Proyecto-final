package co.avanzada.dtos.user;

import co.avanzada.model.Rol;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UpdateProfileDTO {
    @NotNull @NotEmpty @Length(max= 50) String firstName;
    @NotNull @NotEmpty @Length(max = 20) String phone;
    @NotNull @NotEmpty @Length(max= 300) String profilePhoto;
    @NotNull @NotEmpty @Length(max = 500) String descripcion;
    @NotNull @NotEmpty @Length(max = 500) @Email Email email;
    @NotNull @NotEmpty @Max(40) @Min(8) String password;
    @NotNull @NotEmpty @Max(20) @Min(8) Date dateBirth;
    @NotNull @NotEmpty @Max(20) Rol rol;



}
