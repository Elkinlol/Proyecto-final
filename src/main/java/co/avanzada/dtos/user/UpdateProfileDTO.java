package co.avanzada.dtos.user;

import co.avanzada.model.Rol;
import jakarta.validation.Valid;
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
      @NotBlank @Length(max= 100) private String fullName;
      @NotBlank @Length(min= 8) private String numberPhone;
      @NotBlank @Length(max= 300)  private String profilePhoto;
      @NotBlank @Length(max = 500) private String descripcion;
      @Valid @NotNull  private Rol rol;
}
