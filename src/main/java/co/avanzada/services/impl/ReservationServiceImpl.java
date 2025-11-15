package co.avanzada.services.impl;

import co.avanzada.dtos.reservs.CreateReserveDTO;
import co.avanzada.dtos.reservs.ReservDTO;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.ForbiddenException;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.mappers.ReservMapper;
import co.avanzada.model.Listing;
import co.avanzada.model.Promotion;
import co.avanzada.model.Reservations;
import co.avanzada.model.User;
import co.avanzada.model.enunms.ReservationStatus;
import co.avanzada.model.enunms.Status;
import co.avanzada.repository.ListingRepository;
import co.avanzada.repository.PromotionRepository;
import co.avanzada.repository.ReservationRepository;
import co.avanzada.repository.UserRepository;
import co.avanzada.security.AuthUtils;
import co.avanzada.services.ReservationService;
import co.avanzada.services.UserService;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Builder
@Service
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ListingRepository listingRepository;
    private final ReservMapper reservMapper;
    private final AuthUtils authUtils;
    private final EmailService emailService;
    private final PromotionRepository promotionRepository;
    private final int pageSize= 10;



    @Override
    public ReservDTO CreateReserve(CreateReserveDTO createReserve, String listingId) {
        //Se obtienn las entidades
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        Listing listing = findListingById(listingId);

        //Se realizan las validaciones
        validateListingActive(listing.getStatus());
        validateDateRange(createReserve.checkIn(), createReserve.checkOut());
        validateCapacityListing(createReserve.guestCount(), listing.getMaxGuest());
        validateAvailability(listingId, createReserve.checkIn(), createReserve.checkOut());

        //Logica del precio
        long nigths = java.time.temporal.ChronoUnit.DAYS.between(createReserve.checkIn(), createReserve.checkOut());
        BigDecimal totalPrice= listing.getNightlyPrice().multiply(new BigDecimal(nigths));
        if(createReserve.discountCode()!=null  && !createReserve.discountCode().isBlank()) {
            Promotion cupon = validateCupon(createReserve.discountCode(), listingId);
            BigDecimal descuento = totalPrice.multiply(cupon.getPromotion()).divide(BigDecimal.valueOf(100));
            totalPrice = totalPrice.subtract(descuento);
            cupon.setUsed(true);
            promotionRepository.delete(cupon);
        }
        //Definimos datos y guardamos
        Reservations reservation = reservMapper.toEntity(createReserve);
        reservation.setPrice(totalPrice);
        reservation.setUser(user);
        reservation.setReservationsStatus(ReservationStatus.CONFIRMED);
        reservation.setListings(listing);
        reservationRepository.saveAndFlush(reservation);


        //Enviamos los correos
        emailService.sendReservationConfirmation(reservation);
        emailService.sendHostNotification(reservation);

        return reservMapper.toDTO(reservation);
    }

    @Override
    public Page<ReservDTO> getReservations(ReservationStatus estado, LocalDateTime checkIn, LocalDateTime checkOut, int page) {
        User user = getUserById(authUtils.getCurrentUserId());
        if(checkIn != null && checkOut == null || checkIn == null && checkOut!=null){
            throw new ConflictException("Debe enviar las dos fechas");
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Reservations> reservations = reservationRepository.findByFilters(authUtils.getCurrentUserId() ,estado, checkIn, checkOut, pageable);
        return reservations.map(reservMapper::toDTO);
    }

    @Override
    public ReservDTO getReservation(String id) {
        String userId = authUtils.getCurrentUserId();
        getUserById(userId);
        Reservations reservation = getReservationById(id).get();
        return reservMapper.toDTO(reservation);
    }

    @Override
    public void deleteReservation(String id) {
        String userId = authUtils.getCurrentUserId();
        User user = getUserById(userId);
        Reservations reservation = getReservationById(id).get();
        if(!reservation.getUser().getId().equals(user.getId())){
            throw new ForbiddenException("No se puede eliminar una reserva que no le pertenezca");
        }
        if(reservation.getCheckIn().isBefore(LocalDateTime.now().minusDays(2))){
            throw new ConflictException("La reserva no puede ser eliminada, porque quedan 48 horas o menos para el inicio del hospedaje");
        }
        reservation.setReservationsStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);

        emailService.sendHostReservationCancellation(reservation);
        return ;
    }

    @Override
    public Page<ReservDTO> getListingsByReservation(String id, int page) {
        String userId = authUtils.getCurrentUserId();
        User host = getUserById(userId);
        Listing listing = findListingById(id);
        if(!listing.getHost().getId().equals(host.getId())){
            throw new ForbiddenException("Este alojamiento no pertenece a este host");
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        Page <Reservations> reservations = reservationRepository.findAllByListingId(id,userId, pageable);
        return reservations.map(reservMapper::toDTO);
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


    private void  validateListingActive(Status status){
        if(status.equals(Status.INACTIVE)){
            throw new ConflictException("Ya no se pueden realizar reservas de este alojamiento");
        }
    }
    private void validateDateRange(LocalDateTime checkIn, LocalDateTime checkOut){
        if(checkIn.isBefore(LocalDateTime.now()) || !checkOut.isAfter(checkIn)){
            throw new ConflictException("No se puede reservar en fechas pasadas  y mayores a 1 noche");
        }
    }

    private void validateCapacityListing(Integer gestCount ,Integer maxCapacity){
        if(gestCount>maxCapacity){
            throw new ConflictException("No es posible dado que supera el maximo de huespedes");
        }
    }

    private void validateAvailability(String listingId, LocalDateTime checkIn, LocalDateTime checkOut) {
        boolean overlap = reservationRepository.existsByListingAndDateRange(listingId, checkIn, checkOut);
        if (overlap)
            throw new ConflictException("Ya existe una reserva en esas fechas");
    }

    private Optional<Reservations> getReservationById(String id) {
        if(!reservationRepository.findById(id).isPresent()){
            throw new ResourceNotFoundException("Reserva no encontrada");
        }
        return reservationRepository.findById(id);
    }

    private Promotion validateCupon(String code, String listingId) {
        if(!promotionRepository.findByCode(code).isPresent()){
            throw new ResourceNotFoundException("Cupon invalido o expirado");
        }
        Promotion promotion = promotionRepository.findByCode(code).get();
        if (promotion.getListing() != null && !promotion.getListing().getId().equals(listingId)) {
            throw new ConflictException("Este cupón no es válido para este alojamiento");
        }

        return promotion;
    }

}
