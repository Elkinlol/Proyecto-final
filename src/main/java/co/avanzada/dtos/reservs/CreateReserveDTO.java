package co.avanzada.dtos.reservs;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record CreateReserveDTO(
         @NotNull LocalDateTime checkIn,  @NotNull LocalDateTime checkOut,
        @NotNull @Min(1)int gestCount,  @NotNull String listingId) {
}
