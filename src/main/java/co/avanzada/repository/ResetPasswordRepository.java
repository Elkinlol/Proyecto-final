package co.avanzada.repository;

import co.avanzada.model.PasswordResetCode;
import co.avanzada.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<PasswordResetCode, String> {
    Optional<PasswordResetCode> findByCode(String code);
    void save(String code);
    Optional<PasswordResetCode> findByUserAndCode(User user, String code);
}
