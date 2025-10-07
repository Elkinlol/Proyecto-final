package co.avanzada.dtos.listings;

import co.avanzada.model.Adress;
import co.avanzada.model.Host;
import co.avanzada.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

public record ListingDTO(
        @NotBlank @Length (max = 70) String title,
        @Valid @NotNull Host user,
        @NotBlank @Length(max=500)  String description,
        @NotNull BigDecimal nightlyPrice,
        @NotNull Integer maxGuest,
        @NotNull @Size(min = 1, max =10) List<String> images,
        @NotNull AddressDTO adress) {
}
