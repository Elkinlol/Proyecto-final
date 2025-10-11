package co.avanzada.dtos.listings;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MetricsDTO( Float rating,  int cantReservs, String title) {
}
