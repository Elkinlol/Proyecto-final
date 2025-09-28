package co.avanzada.repository;

import co.avanzada.dtos.listings.ListingDTO;
import co.avanzada.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {
}
