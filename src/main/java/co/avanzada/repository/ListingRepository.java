package co.avanzada.repository;

import co.avanzada.dtos.listings.ListingDTO;
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

    Optional<Listing> findById(String id);

    Page<Listing> findListingByHost(User host, Pageable pageable);

    @Query("select r.listings from Reservations r where ( (:checkIn is null and :checkOut is null) or  :checkIn > r.checkOut or :checkIn < r.checkIn and :checkOut > r.checkOut or :checkOut < r.checkIn) and (:city is null or  r.listings.adress.city = :city) and (:nightlyPrice is null or  r.listings.nightlyPrice = :nightlyPrice) ")
    Page<Listing> findByFilter(String city, LocalDate checkIn, LocalDate checkOut, List<Services> services
            , BigDecimal nightlyPrice, Pageable pageable);
}
