package co.avanzada.dtos.listings;

import jakarta.persistence.Column;

public record AddressDTO(
        String adress,
        String city,
        String latitud,
        String longitud
) {
}
