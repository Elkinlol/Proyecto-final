package co.avanzada.services;


import co.avanzada.dtos.reservs.CreateReserveDTO;



public interface ReservationService {
    String CreateReserve( CreateReserveDTO createReserve);
    String getReservations(String estado,String checkIn, String checkOut, String page, String pageSize);
    String getReservation( String id);
    String deleteReservation( String id);
    String getListingsByReservation( String id);
}
