package es.natalia.natminds.model.repository;

import es.natalia.natminds.model.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repositorio de datos para la entidad "Post" que proporciona métodos para acceder y manipular las
 * publicaciones.
 */
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

  /**
   * Obtiene una lista de publicaciones asociadas a un usuario por su ID.
   *
   * @param userId ID del usuario.
   * @return Lista de publicaciones asociadas al usuario.
   */
  List<Post> findByUserUserId(Long userId);
}
