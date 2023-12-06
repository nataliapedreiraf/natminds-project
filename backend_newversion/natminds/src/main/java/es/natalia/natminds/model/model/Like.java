package es.natalia.natminds.model.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

/**
 * Clase de modelo que representa la entidad "Like" para gestionar los likes en las publicaciones.
 */
@Data
@Entity
@Table(name = "likes")
public class Like {

  /** Identificador único del like. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long likeId;

  /** Usuario que dio el like. La relación es de muchos likes a un usuario. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private User user;

  /** Publicación a la que se le dio el like. La relación es de muchos likes a una publicación. */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Post post;
}
