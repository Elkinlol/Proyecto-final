package co.avanzada.services;

import co.avanzada.dtos.listings.CreateListingDTO;
import co.avanzada.dtos.listings.UpdateListingDTO;
import co.avanzada.model.Listing;
import co.avanzada.model.Services;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.List;

public interface ListingService {
    Void createListing( CreateListingDTO CreateListing);
    Void updateListing( String id,  UpdateListingDTO UpdateListing);
    Void deleteListing( String id);
    Listing getListing( String id);
    List <Listing> getListingBySearch(String ciudad, String fecha1,  String fecha2, float  nightlyPrice,List<Services> servicesList);
    List<String> getMetrics( String id, String startDate, String endDate);
}
