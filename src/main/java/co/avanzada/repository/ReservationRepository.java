package co.avanzada.repository;

import co.avanzada.model.Reservations;
import co.avanzada.model.enunms.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservations, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM Reservations r
        WHERE r.listings.id = :listingId
        AND r.reservationsStatus IN ('PENDING', 'CONFIRMED')
        AND (r.checkIn < :checkOut AND r.checkOut > :checkIn)
    """)
    boolean existsByListingAndDateRange(String listingId, LocalDateTime checkIn, LocalDateTime checkOut);

    Optional<Reservations> findById(String id);

    @Query("""
    SELECT r FROM Reservations r
    WHERE r.user.id = :userId
      AND (:estado IS NULL OR r.reservationsStatus = :estado)
      AND (:checkIn IS NULL OR r.checkIn >= :checkIn)
      AND (:checkOut IS NULL OR r.checkOut <= :checkOut)
    ORDER BY r.createdAt DESC
""")
    Page<Reservations> findByFilters(
            String userId,
            ReservationStatus estado,
            LocalDateTime checkIn,
            LocalDateTime checkOut,
            Pageable pageable
    );

    @Query("""
    SELECT r FROM Reservations r
    WHERE r.listings.id = :listingId
      AND r.listings.host.id = :hostId
    ORDER BY r.checkIn DESC
""")
    Page<Reservations> findAllByListingId(
            String listingId,
            String hostId,
            Pageable pageable
    );

    @Query("""
    SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
    FROM Reservations r
    WHERE r.user.id = :userId
      AND r.checkOut < CURRENT_TIMESTAMP
      AND r.reservationsStatus = 'COMPLETED'
""")
    boolean hasAnyCompletedReservation(String userId);


}
