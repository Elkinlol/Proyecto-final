package co.avanzada.dtos.listings;

import co.avanzada.model.Adress;
import co.avanzada.model.Host;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CreateListingDTO(
        @NotEmpty @NotNull @Length (max =100)  String title,
        @Valid @NotEmpty @NotNull Adress adress,
        @Valid @NotEmpty @NotNull Host user,
        @NotEmpty @NotNull @DecimalMin("0.1") float nightlyPrice,
        @NotEmpty @NotNull @Min(1) int maxGuest,
        @NotEmpty @NotNull @Max(500) String description,
        @NotEmpty @NotNull @Min(1) @Max(10) List<String> images
) {
}
