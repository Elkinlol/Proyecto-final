package co.avanzada.dtos;

import co.avanzada.model.Adress;
import co.avanzada.model.Host;

import java.util.List;

public record ListingDTO(
        String title,
        Host user,
        String description,
        float nightlyPrice,
        String maxGuest,
        List<String> images,
        Adress adress) {
}
