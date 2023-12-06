package es.natalia.natminds.model.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Clase de modelo que representa la entidad "Post" para gestionar las publicaciones.
 */
@Data
@Entity
@Table(name = "post")
public class Post {

    /**
     * Identificador único de la publicación.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    /**
     * Usuario que realizó la publicación. La relación es de muchas publicaciones a un usuario.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    /**
     * Texto de la publicación. No puede estar en blanco.
     */
    @NotBlank
    private String text;
}
