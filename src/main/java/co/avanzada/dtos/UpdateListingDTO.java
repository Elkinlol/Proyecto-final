package co.avanzada.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateListingDTO(@NotNull @NotEmpty String title, @NotNull @NotEmpty String description,
                               @NotNull @NotEmpty float nightlyPrice, @NotNull @NotEmpty String maxCapacity,
                               List<@NotEmpty @Size(min = 1, max = 10) String> images) {
}
