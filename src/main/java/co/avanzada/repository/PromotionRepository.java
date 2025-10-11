package co.avanzada.repository;

import co.avanzada.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

    Optional<Promotion> findByCode(String code);

    @Query("""
        SELECT c FROM Promotion c
        WHERE c.code = :code
          AND c.used = true
          AND (c.expirationDate IS NULL OR c.expirationDate >= CURRENT_TIMESTAMP)
    """)
    Optional<Promotion> validateCoupon( String code);
}
