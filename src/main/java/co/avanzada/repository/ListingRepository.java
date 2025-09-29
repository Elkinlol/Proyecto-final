package co.avanzada.repository;

import co.avanzada.dtos.listings.ListingDTO;
import co.avanzada.model.Listing;
import co.avanzada.model.enunms.Services;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ListingRepository extends JpaRepository<Listing, String> {

    Optional<Listing> findById(String id);
    Page<Listing> findByFilter(String city, LocalDateTime checkIn, LocalDateTime checkOut, List<Services> services
            , Pageable pageable, BigDecimal nightlyPrice);
}
