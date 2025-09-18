package co.avanzada.dtos;

import co.avanzada.model.Adress;
import co.avanzada.model.Host;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CreateListingDTO(
        @NotEmpty @NotNull @Length (max =100)  String title,
        @NotEmpty @NotNull Adress adress,
        @NotEmpty @NotNull Host user,
        @NotEmpty @NotNull @DecimalMin("2") float nightlyPrice,
        @NotEmpty @NotNull String maxGuest,
        @NotEmpty @NotNull String description,
        @NotEmpty @NotNull @Min(1) @Max(10) List<String> images



        ) {
}
