package co.avanzada.dtos.reservs;

import co.avanzada.model.enunms.ReservationStatus;
import co.avanzada.model.enunms.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReservDTO(@Valid @NotNull LocalDateTime checkIn, @Valid @NotNull LocalDateTime checkOut,
                        Integer guestCount, @NotNull ReservationStatus status, BigDecimal totalPrice, String id) {
}
