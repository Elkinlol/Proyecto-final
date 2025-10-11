package co.avanzada.dtos.listings;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

public record UpdateListingDTO(  @Length(max = 100) String title,   @Length(max= 500) String description,
                               @DecimalMin("0.1") BigDecimal nightlyPrice,   @Min(1) Integer maxCapacity
                               ) {
}
