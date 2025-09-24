package co.avanzada.repository;

import co.avanzada.dtos.listings.ListingDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListingRepostory extends JpaRepository<ListingDTO, Long> {
}
