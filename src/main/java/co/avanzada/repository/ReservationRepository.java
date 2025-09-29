package co.avanzada.repository;

import co.avanzada.dtos.user.UserDTO;
import co.avanzada.model.Reservations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Long> {
    UserDTO findById(String id);
}
