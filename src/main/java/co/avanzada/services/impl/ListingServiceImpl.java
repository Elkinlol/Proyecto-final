package co.avanzada.services.impl;
import co.avanzada.dtos.listings.*;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.ForbiddenException;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.exception.UnatorizedException;
import co.avanzada.mappers.ListingMapper;
import co.avanzada.model.Adress;
import co.avanzada.model.Host;
import co.avanzada.model.Listing;
import co.avanzada.model.User;
import co.avanzada.model.enunms.Services;
import co.avanzada.model.enunms.Status;
import co.avanzada.repository.AdressRepository;
import co.avanzada.repository.ListingRepository;
import co.avanzada.repository.UserRepository;
import co.avanzada.security.AuthUtils;
import co.avanzada.services.ImageService;
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
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@Builder
@Service
public class ListingServiceImpl implements ListingService {
    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;
    private final UserRepository userRepository;
    private final AdressRepository  adressRepository;
    private final AuthUtils authUtils;
    private final int pageSize =10;
    private final ImageService imageService;

    @Override
    public ListingDTO createListing(CreateListingDTO createListingDTO) {
        String id = authUtils.getCurrentUserId();
        User  user = getUserById(id);
        Listing listing = listingMapper.toEntity(createListingDTO);
        listing.setHost(user);
        Adress adress = Adress.builder()
                .city(createListingDTO.adress().city())
                .adress(createListingDTO.adress().adress())
                .latitud(createListingDTO.adress().latitud())
                .longitud(createListingDTO.adress().longitud())
                .build();
        adress = adressRepository.save(adress);
        listing.setAdress(adress);
        listingRepository.save(listing);
        user.getListings().add(listing);
        return listingMapper.toDTO(listing);
    }

    @Override
    public ListingDTO updateListing(String id, UpdateListingDTO updateListingDTO) {
        String idUser = authUtils.getCurrentUserId();
        Listing listing = findListingById(id);
        validateListingOwner(listing, idUser);
        listingMapper.updateListingFromDTO(updateListingDTO, listing);
        listingRepository.save(listing);
        return listingMapper.toDTO(listing);
    }

    @Override
    public Void deleteListing(String listingId) {
        String idUser = authUtils.getCurrentUserId();
        Listing listing = findListingById(listingId);
        validateListingOwner(listing, idUser);
        if(listingRepository.existsFutureReservations(listingId, LocalDateTime.now())) {
            throw new ConflictException("No se puede eliminaar un alojamiento con reservas futuras");
        }
        listing.setStatus(Status.INACTIVE);
        listingRepository.save(listing);
        return null;
    }

    @Override
    public ListingDTO getListing(String id) {
        String currentUserId = authUtils.getCurrentUserId();
        getUserById(currentUserId);
        Listing listing = findListingById(id);
        if(listing.getStatus().equals(Status.INACTIVE)) {
            throw new ConflictException("El alojamiento ya no existe");
        }
        return listingMapper.toDTO(listing);
    }

    @Override
    public Page<ListingSearchResponseDTO> getListingBySearch(String ciudad, LocalDateTime  fecha1, LocalDateTime  fecha2,
                                                             BigDecimal nightlyPrice, List<Services> servicesList, int page) {

        if(fecha1 != null && fecha2 == null || fecha1 == null && fecha2!=null){
            throw new ConflictException("Debe enviar las dos fechas");
        }

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Listing> listings= listingRepository.findByFilter(ciudad, fecha1, fecha2, servicesList, nightlyPrice, pageable);
        return listings.map(listingMapper::toResponseDTO);
    }


    @Override
    public MetricsDTO getMetrics(String id, LocalDate startDate, LocalDate endDate) {
        String idUser = authUtils.getCurrentUserId();
        Listing listing = findListingById(id);
        validateListingOwner(listing, idUser);
        if(listing.getStatus().equals(Status.INACTIVE)){
            throw new ConflictException("No se pueden ver las metricas de este alojamiento");
        }
        int cantReservs = listingRepository.countReservationsByListingAndDateRange(id, startDate, endDate);
        float rating = listingRepository.averageRatingByListingAndDateRange(id, startDate, endDate);
        return new MetricsDTO(rating, cantReservs, listing.getTitle());
    }

    @Override
    public Page<ListingDTO> getListingFromHost(int page) {
        User  user = getUserById(authUtils.getCurrentUserId());
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Listing> listings = listingRepository.findListingByHost(user, pageable);
        return listings.map(listingMapper::toDTO);
    }

    @Override
    public List<String> uploadListingImages(String listingId, List<MultipartFile> images) throws Exception {
        Listing listing = findListingById(listingId);

        for (MultipartFile image : images) {
            Map uploadResult = imageService.upload(image);
            listing.getImage().add((String) uploadResult.get("secure_url"));
            listing.getImageIds().add((String) uploadResult.get("public_id"));
        }

        listingRepository.save(listing);
        return listing.getImage();
    }

    @Override
    public void deleteListingImage(String listingId, String imageId) throws Exception {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        imageService.delete(imageId);

        int index = listing.getImageIds().indexOf(imageId);
        if (index != -1) {
            listing.getImageIds().remove(index);
            listing.getImage().remove(index);
        }

        listingRepository.save(listing);
    }


    private Listing findListingById(String id){
        if(!listingRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Alojamiento no encontrado");
        }
        return listingRepository.findById(id).get();
    }

    private User getUserById(String id) {
        if(!userRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        return userRepository.findById(id).get();
    }

    private void validateListingOwner (Listing listing, String idUser)  {
        if(!listing.getHost().getId().equals(idUser)){
            throw new ForbiddenException("Este host no es el dueño del alojamiento");
        }

    }
}
