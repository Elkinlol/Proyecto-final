package co.avanzada.services;


import co.avanzada.dtos.reservs.CreateReserveDTO;
import co.avanzada.dtos.reservs.ReservDTO;

import java.util.List;


public interface ReservationService {
    void CreateReserve( CreateReserveDTO createReserve);
    List<ReservDTO> getReservations(String estado, String checkIn, String checkOut, String page, String pageSize);
    ReservDTO getReservation( String id);
    void deleteReservation( String id);
    List<ReservDTO> getListingsByReservation( String id);
}
