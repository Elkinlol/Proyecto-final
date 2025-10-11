package co.avanzada.dtos.listings;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record ListingSearchResponseDTO(
        @NotBlank String title, @NotBlank String fullName, @NotBlank String city, @NotNull BigDecimal nightlyPrice,
        @NotNull int maxGuest, List<String> image) {
}
