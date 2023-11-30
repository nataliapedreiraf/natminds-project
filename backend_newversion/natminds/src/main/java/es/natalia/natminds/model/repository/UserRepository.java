package es.natalia.natminds.model.repository;

import es.natalia.natminds.model.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface UserRepository
        extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
  Optional<User> findByEmailAndPassword(String email, String password);

}
