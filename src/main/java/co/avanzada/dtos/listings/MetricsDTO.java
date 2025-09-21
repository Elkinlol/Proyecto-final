package co.avanzada.dtos.listings;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MetricsDTO(@NotNull @NotEmpty @Size(min= 0, max= 5) Float rating, @NotNull @NotEmpty int cantReservs) {
}
