package co.avanzada.services.impl;

import co.avanzada.dtos.reservs.CreateReserveDTO;
import co.avanzada.dtos.reservs.ReservDTO;
import co.avanzada.services.ReservationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Override
    public void CreateReserve(CreateReserveDTO createReserve) {
        return ;
    }

    @Override
    public List<ReservDTO> getReservations(String estado, String checkIn, String checkOut, String page, String pageSize) {
        return null;
    }

    @Override
    public ReservDTO getReservation(String id) {
        return null;
    }

    @Override
    public void deleteReservation(String id) {
        return ;
    }

    @Override
    public List<ReservDTO> getListingsByReservation(String id) {
        return null;
    }
}
