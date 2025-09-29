package co.avanzada.services.impl;
import co.avanzada.dtos.listings.ListingDTO;
import co.avanzada.dtos.listings.MetricsDTO;
import co.avanzada.dtos.listings.CreateListingDTO;
import co.avanzada.dtos.listings.UpdateListingDTO;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.exception.UnatorizedException;
import co.avanzada.mappers.ListingMapper;
import co.avanzada.model.Host;
import co.avanzada.model.Listing;
import co.avanzada.model.enunms.Services;
import co.avanzada.model.enunms.Status;
import co.avanzada.repository.ListingRepository;
import co.avanzada.services.ListingService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RequiredArgsConstructor
@Builder
@Service
public class ListingServiceImpl implements ListingService {
    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;


    @Override
    public ListingDTO createListing(CreateListingDTO createListingDTO) {
        Host host = createListingDTO.user();
        Listing listing = listingMapper.toEntity(createListingDTO);
        listingRepository.save(listing);
        return listingMapper.toDTO(listing);
    }

    @Override
    public ListingDTO updateListing(String id, UpdateListingDTO updateListingDTO) {
        Listing listing = findListingById(id);
        listingMapper.updateListingFromDTO(updateListingDTO, listing);
        listingRepository.save(listing);
        return listingMapper.toDTO(listing);
    }

    @Override
    public Void deleteListing(String id) {
        Listing listing = findListingById(id);
        listing.setStatus(Status.INACTIVE);
        listingRepository.save(listing);
        return null;
    }

    @Override
    public ListingDTO getListing(String id) {
        Listing listing = findListingById(id);
        return listingMapper.toDTO(listing);
    }

    @Override
    public Page<ListingDTO> getListingBySearch(String ciudad, LocalDate fecha1, LocalDate fecha2,
                                               BigDecimal nightlyPrice, List<Services> servicesList, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Listing> listings= listingRepository.findByFilter(ciudad, fecha1, fecha2, servicesList, pageable, nightlyPrice);
        return listings.map(listingMapper::toDTO);
    }



    public MetricsDTO getMetrics(String id, String startDate, String endDate) {
        return null;
    }



    private Listing findListingById(String id){
        if(!listingRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Alojamiento no encontrado");
        }
        return listingRepository.findById(id).get();
    }
}
