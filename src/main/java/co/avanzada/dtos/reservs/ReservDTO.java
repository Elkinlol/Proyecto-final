package co.avanzada.dtos.reservs;

import co.avanzada.model.enunms.Status;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReservDTO(@Valid @NotNull LocalDate checkIn, @Valid @NotNull LocalDate checkOut,
                        int gestCount, @Valid @NotNull String Idlisting, @NotNull Status status) {
}
