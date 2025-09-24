package co.avanzada.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyReservation extends JpaRepository<ReplyReservation, Long> {
}
