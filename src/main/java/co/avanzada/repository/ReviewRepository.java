package co.avanzada.repository;

import co.avanzada.dtos.review.ReviewDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDTO, Long> {
}
