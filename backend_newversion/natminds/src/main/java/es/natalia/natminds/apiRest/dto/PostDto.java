package es.natalia.natminds.apiRest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/*DTO para la funcionalidad de Create (@GET)
 *
 * En este caso PostDto tendrá los mismos atributos que la clase Post, ya que queremos recuperar todos los campos. */
@Data
public class PostDto {
    private Long postId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Long userId;

    private String text;

    private Long likeCount;


}
