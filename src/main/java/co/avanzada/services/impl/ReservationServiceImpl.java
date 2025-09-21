package co.avanzada.services.impl;

import co.avanzada.dtos.reservs.CreateReserveDTO;
import co.avanzada.services.ReservationService;
import org.springframework.stereotype.Service;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Override
    public String CreateReserve(CreateReserveDTO createReserve) {
        return "";
    }

    @Override
    public String getReservations(String estado, String checkIn, String checkOut, String page, String pageSize) {
        return "";
    }

    @Override
    public String getReservation(String id) {
        return "";
    }

    @Override
    public String deleteReservation(String id) {
        return "";
    }

    @Override
    public String getListingsByReservation(String id) {
        return "";
    }
}
