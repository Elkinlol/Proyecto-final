package co.avanzada.dtos.listings;

import co.avanzada.dtos.user.HostDTO;
import co.avanzada.model.Adress;
import co.avanzada.model.Host;
import co.avanzada.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

public record ListingDTO(
        @NotBlank  String id,
        @NotBlank @Length (max = 70) String title,
        @Valid @NotNull HostDTO host,
        @NotBlank @Length(max=500)  String description,
        @NotNull BigDecimal nightlyPrice,
        @NotNull int maxGuest,
        @NotNull @Size(min = 1, max =10) List<String> image,
        @NotNull AddressDTO adress,
        @NotNull List<String> services,
        @NotNull float averageRating) {
}
