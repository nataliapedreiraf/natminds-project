package es.natalia.natminds.apiRest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*DTO para la funcionalidad de Create (@GET)
 *
 * En este caso PostDto tendrá los mismos atributos que la clase Post, ya que queremos recuperar todos los campos. */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long postId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Long userId;

    private String text;

    private Long likeCount;


}
