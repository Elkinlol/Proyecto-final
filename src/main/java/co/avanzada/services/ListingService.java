package co.avanzada.services;

import co.avanzada.dtos.listings.*;
import co.avanzada.model.Listing;
import co.avanzada.model.enunms.Services;
import org.springframework.data.domain.Page;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ListingService {
    ListingDTO createListing(CreateListingDTO CreateListing);
    ListingDTO updateListing( String id,  UpdateListingDTO UpdateListing);
    Void deleteListing( String id);
    ListingDTO getListing( String id);
    Page <ListingSearchResponseDTO> getListingBySearch(String ciudad, LocalDate fecha1, LocalDate fecha2,
                                                       BigDecimal nightlyPrice, List<Services> servicesList, int page);
    MetricsDTO getMetrics(String id, LocalDate startDate, LocalDate endDate);
    Page <ListingDTO> getListingFromHost(int page);
}
