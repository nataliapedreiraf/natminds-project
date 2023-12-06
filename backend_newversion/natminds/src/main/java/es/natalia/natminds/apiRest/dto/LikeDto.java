package es.natalia.natminds.apiRest.dto;

/** Clase de transferencia de datos (DTO) utilizada para gestionar "likes". */
public class LikeDto {

  /** ID del usuario asociado al "like". */
  private Long userId;

  /** ID de la publicación a la que se le da el "like". */
  private Long postId;

  /**
   * Obtiene el ID del usuario asociado al "like".
   *
   * @return El ID del usuario.
   */
  public Long getUserId() {
    return userId;
  }

  /**
   * Establece el ID del usuario asociado al "like".
   *
   * @param userId El nuevo ID del usuario.
   */
  public void setUserId(Long userId) {
    this.userId = userId;
  }

  /**
   * Obtiene el ID de la publicación a la que se le da el "like".
   *
   * @return El ID de la publicación.
   */
  public Long getPostId() {
    return postId;
  }

  /**
   * Establece el ID de la publicación a la que se le da el "like".
   *
   * @param postId El nuevo ID de la publicación.
   */
  public void setPostId(Long postId) {
    this.postId = postId;
  }
}
