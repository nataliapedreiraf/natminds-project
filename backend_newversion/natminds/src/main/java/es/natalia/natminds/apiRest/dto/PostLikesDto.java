package es.natalia.natminds.apiRest.dto;

import lombok.Data;

/**
 * Clase de transferencia de datos (DTO) utilizada para representar la cantidad de "likes" de una
 * publicación.
 */
@Data
public class PostLikesDto {

  /** ID de la publicación. */
  private Long postId;

  /** Cantidad de "likes" para la publicación. */
  private Long likeCount;
}
