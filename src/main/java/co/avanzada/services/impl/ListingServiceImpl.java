package co.avanzada.services.impl;
import co.avanzada.dtos.listings.MetricsDTO;
import co.avanzada.dtos.listings.CreateListingDTO;
import co.avanzada.dtos.listings.UpdateListingDTO;
import co.avanzada.model.Listing;
import co.avanzada.model.Reservations;
import co.avanzada.model.Review;
import co.avanzada.model.Services;
import co.avanzada.services.ListingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ListingServiceImpl implements ListingService {
    @Override
    public Void createListing(CreateListingDTO CreateListing) {
        return null;
    }

    @Override
    public Void updateListing(String id, UpdateListingDTO UpdateListing) {
        return null;
    }

    @Override
    public Void deleteListing(String id) {
        return null;
    }

    @Override
    public Listing getListing(String id) {
        return null;
    }

    @Override
    public List<Listing> getListingBySearch(String ciudad, String fecha1, String fecha2, float nightlyPrice, List<Services> servicesList) {
        return List.of();
    }


    /*@Override
    public MetricsDTO getMetrics(String id, String startDate, String endDate) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Listing not found"));

        LocalDate start = (startDate != null) ? LocalDate.parse(startDate) : null;
        LocalDate end = (endDate != null) ? LocalDate.parse(endDate) : null;

        // Filtrar reservas por rango de fechas si se proporcionan
        List<Reservations> filteredReservations = listing.getReservations().stream()
                .filter(r -> {
                    if (start != null && end != null) {
                        return !(r.getCheckOut().isBefore(start) || r.getCheckIn().isAfter(end));
                    }
                    return true;
                })
                .toList();

        Long totalReservations = (long) filteredReservations.size();

        // Filtrar calificaciones relacionadas con las reservas filtradas
        Double averageRating = filteredReservations.stream()
                .flatMap(r -> r.getReviews().stream())
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        return new MetricsDTO(totalReservations, averageRating);
    }*/
    public Void getMetrics(String id, String startDate, String endDate){
        return null;
    }
}
