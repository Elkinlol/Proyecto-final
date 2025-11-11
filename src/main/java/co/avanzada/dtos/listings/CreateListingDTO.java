package co.avanzada.dtos.listings;

import co.avanzada.model.Adress;
import co.avanzada.model.Host;
import co.avanzada.model.enunms.Services;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import java.math.BigDecimal;
import java.util.List;

public record CreateListingDTO(
        @NotBlank @Length (max =100)  String title,
        @Valid  @NotNull AddressDTO adress,
        @NotNull @DecimalMin("0.1") BigDecimal nightlyPrice,
        @NotNull @Min(1) Integer maxGuest,
        @NotBlank @Length(max = 500) String description,
        @NotEmpty @NotNull @Size(min = 1, max = 10) List<String> images,
        @NotNull @NotEmpty List<Services> services
) {
}
