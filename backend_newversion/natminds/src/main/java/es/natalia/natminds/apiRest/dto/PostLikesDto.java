package es.natalia.natminds.apiRest.dto;

import lombok.Data;

@Data
public class PostLikesDto {
  private Long postId;
  private Long likeCount;
}
