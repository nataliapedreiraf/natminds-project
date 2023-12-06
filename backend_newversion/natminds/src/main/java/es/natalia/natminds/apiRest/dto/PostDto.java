package es.natalia.natminds.apiRest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase de transferencia de datos (DTO) utilizada para representar información de publicaciones.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    /**
     * ID de la publicación.
     */
    private Long postId;

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

    /**
     * Cantidad de "likes" para la publicación.
     */
    private Long likeCount;

    /**
     * Nombre de usuario asociado a la publicación.
     */
    private String userName;

    /**
     * Nombre completo del usuario asociado a la publicación.
     */
    private String userFullName;
}
