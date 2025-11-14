package co.avanzada.repository;

import co.avanzada.model.Listing;
import co.avanzada.model.User;
import co.avanzada.model.enunms.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, String> {

    // Obtener por ID
    Optional<Listing> findById(String id);

    // Obtener listings por host

    @Query("""
    SELECT l 
    FROM Listing l
    WHERE l.host = :host
      AND l.status = 'ACTIVE'
""")
    Page<Listing> findListingByHost(User host, Pageable pageable);

    // Verificar si existen reservas futuras para un listing
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM Reservations r
        WHERE r.listings.id = :listingId
          AND r.checkOut > :checkOut
    """)
    boolean existsFutureReservations(String listingId, LocalDateTime checkOut);

    // Filtrado avanzado de listings
    @Query("""
    SELECT l FROM Listing l
    WHERE l.status = 'ACTIVE'
      AND (:city IS NULL OR LOWER(l.adress.city) LIKE LOWER(CONCAT('%', :city, '%')))
      AND (:nightlyPrice IS NULL OR l.nightlyPrice <= :nightlyPrice)
      AND (:services IS NULL OR EXISTS (
            SELECT s FROM l.services s WHERE s IN :services
      ))
      AND (
          :checkIn IS NULL OR :checkOut IS NULL OR NOT EXISTS (
              SELECT r.id 
              FROM Reservations r 
              WHERE r.listings.id = l.id
                AND r.checkIn <= :checkOut
                AND r.checkOut >= :checkIn
          )
      )
""")
    Page<Listing> findByFilter(String city, LocalDateTime  checkIn, LocalDateTime  checkOut, List<Services> services,
                               BigDecimal nightlyPrice, Pageable pageable);

    // Calcular rating promedio en un rango de fechas
    @Query("""
        SELECT COALESCE(AVG(r.rating), 0) 
        FROM Review r 
        WHERE r.listing.id = :listingId 
          AND (:startDate IS NULL OR r.createdAt BETWEEN :startDate AND :endDate)
    """)
    float averageRatingByListingAndDateRange(
            String listingId,
            LocalDate startDate,
            LocalDate endDate
    );

    // Contar reservas en un rango de fechas
    @Query("""
    SELECT COUNT(r) 
    FROM Reservations r 
    WHERE r.listings.id = :listingId
      AND (
            :checkIn IS NULL OR :checkOut IS NULL
            OR (r.checkIn <= :checkOut AND r.checkOut >= :checkIn)
      )
""")
    int countReservationsByListingAndDateRange(
            String listingId,
            LocalDate checkIn,
            LocalDate checkOut
    );
}
