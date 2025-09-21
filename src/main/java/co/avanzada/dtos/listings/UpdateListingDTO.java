package co.avanzada.dtos.listings;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

public record UpdateListingDTO( @NotBlank @Length(max = 100) String title,  @NotBlank @Length(max= 500) String description,
                               @NotNull @DecimalMin("0.1") BigDecimal nightlyPrice, @NotNull @NotEmpty @Min(1) int maxCapacity,
                               List<@NotEmpty @Size(min = 1, max = 10) String> images) {
}
