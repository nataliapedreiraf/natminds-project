package es.natalia.natminds.apiRest.dto;

public class LikeDto {
  private Long userId;
  private Long postId;

  // Constructores, getters y setters

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }
}
