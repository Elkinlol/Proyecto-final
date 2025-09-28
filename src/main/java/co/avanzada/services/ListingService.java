package co.avanzada.services;

import co.avanzada.dtos.listings.CreateListingDTO;
import co.avanzada.dtos.listings.MetricsDTO;
import co.avanzada.dtos.listings.UpdateListingDTO;
import co.avanzada.model.Listing;
import co.avanzada.model.enunms.Services;


import java.util.List;

public interface ListingService {
    Void createListing( CreateListingDTO CreateListing);
    Void updateListing( String id,  UpdateListingDTO UpdateListing);
    Void deleteListing( String id);
    Listing getListing( String id);
    List <Listing> getListingBySearch(String ciudad, String fecha1,  String fecha2, float  nightlyPrice,List<Services> servicesList);
    MetricsDTO getMetrics(String id, String startDate, String endDate);
}
