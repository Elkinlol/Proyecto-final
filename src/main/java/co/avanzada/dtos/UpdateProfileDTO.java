package co.avanzada.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter





public class UpdateProfileDTO {
    @NotNull @NotEmpty @Length(max= 50) String firstName;
    @NotNull @NotEmpty @Length(max = 20) String celphone;
    @NotNull @NotEmpty @Length(max= 300) String profilePhoto;
    @NotNull @NotEmpty @Length(max = 500) String descripcion;

}
