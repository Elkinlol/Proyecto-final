package co.avanzada.repository;


import co.avanzada.dtos.user.UserDTO;
import co.avanzada.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Void save (User user );
    User findByEmail(String email);
}
