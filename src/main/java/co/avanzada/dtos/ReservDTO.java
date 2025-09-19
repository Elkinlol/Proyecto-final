package co.avanzada.dtos;

import co.avanzada.model.Host;
import co.avanzada.model.Listing;
import co.avanzada.model.User;

public record ReservDTO(Host user, User guest, int gestCount, float price, Listing listing) {
}
