package es.natalia.natminds.model.repository;

import es.natalia.natminds.model.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositorio de datos para la entidad "User" que proporciona métodos para acceder y manipular los
 * usuarios.
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

  /**
   * Busca un usuario por su correo electrónico y contraseña.
   *
   * @param email Correo electrónico del usuario.
   * @param password Contraseña del usuario.
   * @return Usuario encontrado (si existe).
   */
  Optional<User> findByEmailAndPassword(String email, String password);

  /**
   * Obtiene una lista de usuarios que el usuario actual puede seguir.
   *
   * @param currentUserId ID del usuario actual.
   * @return Lista de usuarios sugeridos para seguir.
   */
  @Query("SELECT u FROM User u WHERE u.userId <> :currentUserId")
  List<User> findUsersToFollow(Long currentUserId);
}
