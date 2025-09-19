package co.avanzada.dtos.listings;

import jakarta.validation.constraints.*;

import java.util.List;

public record UpdateListingDTO(@NotNull @NotEmpty @Max(100) String title, @NotNull @NotEmpty @Max(500) String description,
                               @NotNull @NotEmpty @DecimalMin("0.01") float nightlyPrice, @NotNull @NotEmpty @Min(1) int maxCapacity,
                               List<@NotEmpty @Size(min = 1, max = 10) String> images) {
}
