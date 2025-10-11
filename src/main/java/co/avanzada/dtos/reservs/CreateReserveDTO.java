package co.avanzada.dtos.reservs;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;


public record CreateReserveDTO(
        @NotNull LocalDateTime checkIn, @NotNull LocalDateTime checkOut,
        @NotNull @Min(1)Integer guestCount, String discountCode) {
}
