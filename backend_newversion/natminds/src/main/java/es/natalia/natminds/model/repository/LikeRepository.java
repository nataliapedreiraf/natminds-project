package es.natalia.natminds.model.repository;

import es.natalia.natminds.model.model.Like;
import es.natalia.natminds.model.model.Post;
import es.natalia.natminds.model.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositorio de datos para la entidad "Like" que proporciona métodos para acceder y manipular los
 * likes.
 */
public interface LikeRepository extends JpaRepository<Like, Long>, JpaSpecificationExecutor<Like> {

  /**
   * Consulta que devuelve una lista de objetos que contienen cada publicación y la cantidad de
   * likes asociados.
   *
   * @return Lista de objetos con información de la publicación y el conteo de likes.
   */
  @Query(
      "SELECT p, COUNT(l) AS likeCount FROM Post p LEFT JOIN Like l ON p.postId = l.post.postId GROUP BY p")
  List<Object[]> findAllPostsAndLikeCount();

  /**
   * Obtiene el conteo de likes para una publicación específica.
   *
   * @param post Publicación para la que se desea obtener el conteo de likes.
   * @return Cantidad de likes para la publicación.
   */
  Long countByPost(Post post);

  /**
   * Verifica si ya existe un like registrado por un usuario en una publicación.
   *
   * @param user Usuario que dio el like.
   * @param post Publicación en la que se dio el like.
   * @return true si el like existe, false en caso contrario.
   */
  boolean existsByUserAndPost(User user, Post post);

  /**
   * Elimina un like específico dado por un usuario en una publicación.
   *
   * @param user Usuario que dio el like.
   * @param post Publicación en la que se dio el like.
   */
  void deleteByUserAndPost(User user, Post post);
}
