package co.avanzada.services;

import co.avanzada.dtos.listings.*;
import co.avanzada.model.Listing;
import co.avanzada.model.enunms.Services;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ListingService {
    ListingDTO createListing(CreateListingDTO CreateListing);
    ListingDTO updateListing( String id,  UpdateListingDTO UpdateListing);
    Void deleteListing( String id);
    ListingDTO getListing( String id);
    Page <ListingSearchResponseDTO> getListingBySearch(String ciudad, LocalDateTime fecha1, LocalDateTime  fecha2,
                                                       BigDecimal nightlyPrice, List<Services> servicesList, int page);
    MetricsDTO getMetrics(String id, LocalDate startDate, LocalDate endDate);
    Page <ListingDTO> getListingFromHost(int page);
    List<String> uploadListingImages(String listingId, List<MultipartFile> images)throws Exception;
    void deleteListingImage(String listingId, String imageId)throws Exception;
}
