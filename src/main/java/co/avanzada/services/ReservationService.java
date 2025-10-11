package co.avanzada.services;


import co.avanzada.dtos.reservs.CreateReserveDTO;
import co.avanzada.dtos.reservs.ReservDTO;
import co.avanzada.model.enunms.ReservationStatus;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;


public interface ReservationService {
    ReservDTO CreateReserve( CreateReserveDTO createReserve, String listingId);
    Page<ReservDTO> getReservations(ReservationStatus estado, LocalDate checkIn, LocalDate checkOut, int page);
    ReservDTO getReservation( String id);
    void deleteReservation( String id);
    Page<ReservDTO> getListingsByReservation( String id, int page);
}
