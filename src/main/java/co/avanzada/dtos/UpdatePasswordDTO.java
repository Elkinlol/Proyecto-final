package co.avanzada.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter

public class UpdatePasswordDTO {
    @NotNull @NotEmpty @Min(8) String oldPassword;
    @NotNull @NotEmpty @Min(8) String newPassword;

}
