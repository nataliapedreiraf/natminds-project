package es.natalia.natminds.model.repository;

import es.natalia.natminds.model.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository
        extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
  Optional<User> findByEmailAndPassword(String email, String password);

  @Query("SELECT u FROM User u WHERE u.userId <> :currentUserId")
  List<User> findUsersToFollow(Long currentUserId);

}
