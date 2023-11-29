package es.natalia.natminds.apiRest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class CreatePostDto {
  @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Long userId;
  private String text;
}
