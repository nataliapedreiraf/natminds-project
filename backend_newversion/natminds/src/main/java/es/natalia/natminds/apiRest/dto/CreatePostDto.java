package es.natalia.natminds.apiRest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Clase de transferencia de datos (DTO) utilizada para crear una nueva publicación.
 */
@Data
public class CreatePostDto {

  /**
   * ID del usuario asociado a la publicación.
   * Se ignoran ciertas propiedades para evitar problemas con la inicialización lazy de Hibernate.
   */
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Long userId;

  /**
   * Texto de la publicación.
   */
  private String text;
}
