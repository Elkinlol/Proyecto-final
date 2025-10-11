package co.avanzada.dtos.reservs;

import co.avanzada.model.enunms.ReservationStatus;
import co.avanzada.model.enunms.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservDTO(@Valid @NotNull LocalDate checkIn, @Valid @NotNull LocalDate checkOut,
                        Integer gestCount, @NotNull ReservationStatus status, BigDecimal totalPrice) {
}
