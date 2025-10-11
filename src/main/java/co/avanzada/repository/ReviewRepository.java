package co.avanzada.repository;

import co.avanzada.dtos.review.ReviewDTO;
import co.avanzada.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
    SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
    FROM Review r
    WHERE r.user.id = :userId
      AND r.listing.id = :listingId
""")
    boolean existsByUserAndListing( String userId, String listingId);

    @Query("SELECT COALESCE(AVG(r.rating), 0) FROM Review r WHERE r.listing.id = :listingId")
    float getAverageRating( String listingId);

    @Query("""
    SELECT r
    FROM Review r
    WHERE r.listing.id = :listingId
    ORDER BY r.createdAt DESC
""")
    Page<ReviewDTO> findReviewsByListing(String listingId, Pageable pageable);

}
