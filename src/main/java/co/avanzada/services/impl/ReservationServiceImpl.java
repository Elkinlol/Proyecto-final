package co.avanzada.services.impl;

import co.avanzada.dtos.reservs.CreateReserveDTO;
import co.avanzada.dtos.reservs.ReservDTO;
import co.avanzada.exception.ConflictException;
import co.avanzada.exception.ForbiddenException;
import co.avanzada.exception.ResourceNotFoundException;
import co.avanzada.mappers.ReservMapper;
import co.avanzada.model.Listing;
import co.avanzada.model.Reservations;
import co.avanzada.model.User;
import co.avanzada.model.enunms.ReservationStatus;
import co.avanzada.repository.ListingRepository;
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
    private final int pageSize= 10;



    @Override
    public ReservDTO CreateReserve(CreateReserveDTO createReserve, String listingId) {
        //Se obtienn las entidades
        String id = authUtils.getCurrentUserId();
        User user = getUserById(id);
        Listing listing = findListingById(listingId);

        //Se realizan las validaciones
        validateDateRange(createReserve.checkIn(), createReserve.checkOut());
        validateCapacityListing(createReserve.gestCount(), listing.getMaxGuest());
        validateAvailability(listingId, createReserve.checkIn(), createReserve.checkOut());

        //Definimos datos y guardamos
        long nigths = java.time.temporal.ChronoUnit.DAYS.between(createReserve.checkIn(), createReserve.checkOut());
        BigDecimal totalPrice= listing.getNightlyPrice().multiply(new BigDecimal(nigths));
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
    public Page<ReservDTO> getReservations(ReservationStatus estado, LocalDate checkIn, LocalDate checkOut, int page) {
        User user = getUserById(authUtils.getCurrentUserId());
        if(checkIn != null && checkOut == null || checkIn == null && checkOut!=null){
            throw new ConflictException("Debe enviar las dos fechas");
        }
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Reservations> reservations = reservationRepository.findByFilters(estado, checkIn, checkOut, pageable);
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
        if(!user.getReservations().contains(reservation)){
            throw new ForbiddenException("No se puede eliminar una reserva que no le pertenezca");
        }
        if(!reservation.getCheckIn().isBefore(LocalDate.now().minusDays(2))){
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
        Page <Reservations> reservations = reservationRepository.findAllByListingId(id, pageable);
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

    private void validateDateRange(LocalDate checkIn, LocalDate checkOut){
        if(checkIn.isBefore(LocalDate.now()) || !checkOut.isAfter(checkIn)){
            throw new ConflictException("No se puede reservar en fechas pasadas  y mayores a 1 noche");
        }
    }

    private void validateCapacityListing(Integer gestCount ,Integer maxCapacity){
        if(gestCount>maxCapacity){
            throw new ConflictException("No es posible dado que supera el maximo de huespedes");
        }
    }

    private void validateAvailability(String listingId, LocalDate checkIn, LocalDate checkOut) {
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

}
