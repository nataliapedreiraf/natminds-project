package es.natalia.natminds.apiModel.service;

import es.natalia.natminds.model.model.Post;
import java.util.List;

/**
 * Interfaz que define el contrato para gestionar operaciones relacionadas con publicaciones
 * (posts).
 */
public interface PostService {

  /**
   * Obtiene la publicación con el ID especificado.
   *
   * @param postId El ID de la publicación que se desea obtener.
   * @return La publicación con el ID especificado.
   */
  public Post getPost(Long postId);

  /**
   * Crea una nueva publicación.
   *
   * @param post La publicación que se va a crear.
   * @return La publicación recién creada.
   */
  public Post createPost(Post post);

  /**
   * Elimina la publicación con el ID especificado.
   *
   * @param postId El ID de la publicación que se desea eliminar.
   */
  public void removePost(Long postId);

  /**
   * Obtiene una lista de publicaciones asociadas al ID de usuario especificado.
   *
   * @param userId El ID del usuario para el cual se buscan publicaciones.
   * @return Lista de publicaciones asociadas al usuario.
   */
  List<Post> findByUserUserId(Long userId);

  /**
   * Obtiene todas las publicaciones.
   *
   * @return Lista de todas las publicaciones.
   */
  public List<Post> findAll();

  /**
   * Obtiene una lista de todas las publicaciones con sus respectivas cuentas de "likes".
   *
   * @return Lista de arreglos de objetos que contienen información de la publicación y su cuenta de
   *     "likes".
   */
  List<Object[]> findAllPostsWithLikeCount();

  /**
   * Obtiene la cantidad de "likes" para la publicación con el ID especificado.
   *
   * @param postId El ID de la publicación para la cual se desea obtener la cantidad de "likes".
   * @return El número de "likes" para la publicación.
   */
  Long getLikeCountForPost(Long postId);
}
