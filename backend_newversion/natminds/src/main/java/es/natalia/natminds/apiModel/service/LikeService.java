package es.natalia.natminds.apiModel.service;

import es.natalia.natminds.model.model.Post;
import es.natalia.natminds.model.model.User;
import org.springframework.transaction.annotation.Transactional;

/** Interfaz que define el contrato para gestionar los "likes" en publicaciones (posts). */
@Transactional
public interface LikeService {

  /**
   * Agrega un "like" a la publicación especificada por el usuario dado.
   *
   * @param userId El ID del usuario que está dando "like" a la publicación.
   * @param postId El ID de la publicación que se va a "likear".
   */
  public void likePost(Long userId, Long postId);

  /**
   * Obtiene la cantidad de "likes" para la publicación especificada.
   *
   * @param post La publicación para la cual se desea obtener la cantidad de "likes".
   * @return El número de "likes" para la publicación.
   */
  Long getLikeCountForPost(Post post);

  /**
   * Verifica si el usuario especificado ha dado "like" a la publicación dada.
   *
   * @param userId El ID del usuario a verificar si le gusta la publicación.
   * @param postId El ID de la publicación a verificar para "likes".
   * @return true si el usuario le dio "like" a la publicación, false en caso contrario.
   */
  public boolean userLikedPost(Long userId, Long postId);

  /**
   * Elimina el "like" del usuario especificado de la publicación dada.
   *
   * @param user El usuario cuyo "like" se va a eliminar.
   * @param post La publicación de la cual se va a eliminar el "like".
   */
  public void unlikePost(User user, Post post);
}
