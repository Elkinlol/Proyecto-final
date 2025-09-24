package co.avanzada.dtos.reservs;

import co.avanzada.model.Host;
import co.avanzada.model.Listing;
import co.avanzada.model.Status;
import co.avanzada.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Date;

public record ReservDTO(@Valid @NotNull LocalDate checkIn, @Valid @NotNull LocalDate checkOut,
                        int gestCount, @Valid @NotNull String Idlisting, @NotNull Status status) {
}
