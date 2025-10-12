package co.avanzada.services;


import co.avanzada.dtos.listings.*;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.ForbiddenException;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.mappers.ListingMapper;
import co.avanzada.model.*;
import co.avanzada.model.enunms.Services;
import co.avanzada.model.enunms.Status;
import co.avanzada.repository.AdressRepository;
import co.avanzada.repository.ListingRepository;
import co.avanzada.repository.UserRepository;
import co.avanzada.security.AuthUtils;
import co.avanzada.services.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListingServiceTest {

    @Mock
    private ListingRepository listingRepository;
    @Mock
    private ListingMapper listingMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AdressRepository adressRepository;
    @Mock
    private AuthUtils authUtils;
    @Mock
    private ImageService imageService;

    @InjectMocks
    private ListingService listingService;

    private User hostUser;
    private Listing listing;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        hostUser = User.builder()
                .id("host1")
                .listings(new ArrayList<>())
                .build();

        listing = Listing.builder()
                .id("listing1")
                .host(hostUser)
                .status(Status.ACTIVE)
                .image(new ArrayList<>())
                .imageIds(new ArrayList<>())
                .build();

        when(authUtils.getCurrentUserId()).thenReturn("host1");
        when(userRepository.findById("host1")).thenReturn(Optional.of(hostUser));
        when(listingRepository.findById("listing1")).thenReturn(Optional.of(listing));
    }

    @Test
    void createListing_shouldReturnListingDTO() {
        CreateListingDTO dto = listingMapper.toDTO(listing);
        Adress adress = Adress.builder().build();
        ListingDTO listingDTO = listingMapper.toDTO(listing);

        when(listingMapper.toEntity(dto)).thenReturn(listing);
        when(adressRepository.save(any())).thenReturn(adress);
        when(listingMapper.toDTO(listing)).thenReturn(listingDTO);

        ListingDTO result = listingService.createListing(dto);

        assertEquals(listingDTO, result);
        verify(listingRepository).save(listing);
        assertTrue(hostUser.getListings().contains(listing));
    }

    @Test
    void updateListing_shouldUpdateAndReturnDTO() {
        UpdateListingDTO updateListingDTO= new UpdateListingDTO("Bacano", "Lo mejor", 40640.0, 3);
        listingMapper.updateListingFromDTO(updateListingDTO, listing);
        listingRepository.save(listing);
        ListingDTO listingDTO = listingMapper.toDTO(listing);

        when(listingMapper.toDTO(listing)).thenReturn(listingDTO);

        ListingDTO result = listingService.updateListing("listing1", dto);

        verify(listingMapper).updateListingFromDTO(dto, listing);
        verify(listingRepository).save(listing);
        assertEquals(listingDTO, result);
    }

    @Test
    void deleteListing_shouldSetStatusInactive() {
        when(listingRepository.existsFutureReservations("listing1", LocalDateTime.now())).thenReturn(false);

        listingService.deleteListing("listing1");

        assertEquals(Status.INACTIVE, listing.getStatus());
        verify(listingRepository).save(listing);
    }

    @Test
    void deleteListing_shouldThrowConflict_ifHasFutureReservations() {
        when(listingRepository.existsFutureReservations("listing1", LocalDateTime.now())).thenReturn(true);

        assertThrows(ConflictException.class, () -> listingService.deleteListing("listing1"));
    }

    @Test
    void getListing_shouldReturnDTO() {
        ListingDTO listingDTO = new ListingDTO();
        when(listingMapper.toDTO(listing)).thenReturn(listingDTO);

        ListingDTO result = listingService.getListing("listing1");

        assertEquals(listingDTO, result);
    }

    @Test
    void getListingBySearch_shouldReturnPage() {
        Page<Listing> listingPage = new PageImpl<>(List.of(listing));
        Page<ListingSearchResponseDTO> responsePage = new PageImpl<>(List.of(new ListingSearchResponseDTO()));

        when(listingRepository.findByFilter(eq("City"), any(), any(), anyList(), any(), any(Pageable.class)))
                .thenReturn(listingPage);
        when(listingMapper.toResponseDTO(listing)).thenReturn(new ListingSearchResponseDTO());

        Page<ListingSearchResponseDTO> result = listingService.getListingBySearch("City", null, null, null, new ArrayList<>(), 0);

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void uploadListingImages_shouldUploadAllImages() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        Map<String, String> uploadResult = new HashMap<>();
        uploadResult.put("secure_url", "url1");
        uploadResult.put("public_id", "id1");

        when(imageService.upload(file1)).thenReturn(uploadResult);
        when(imageService.upload(file2)).thenReturn(uploadResult);

        List<String> result = listingService.uploadListingImages("listing1", List.of(file1, file2));

        assertEquals(2, result.size());
        assertEquals("url1", result.get(0));
        assertEquals("url1", result.get(1));
        verify(listingRepository).save(listing);
    }

    @Test
    void deleteListingImage_shouldRemoveImage() throws Exception {
        listing.getImage().add("url1");
        listing.getImageIds().add("id1");

        listingService.deleteListingImage("listing1", "id1");

        assertTrue(listing.getImage().isEmpty());
        assertTrue(listing.getImageIds().isEmpty());
        verify(imageService).delete("id1");
        verify(listingRepository).save(listing);
    }

    @Test
    void validateListingOwner_shouldThrowForbidden() {
        User otherUser = User.builder().id("other").build();
        listing.setHost(otherUser);

        assertThrows(ForbiddenException.class, () -> listingService.updateListing("listing1", new UpdateListingDTO()));
    }

    @Test
    void findListingById_shouldThrowResourceNotFound_ifNotExist() {
        when(listingRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> listingService.getListing("missing"));
    }
}

