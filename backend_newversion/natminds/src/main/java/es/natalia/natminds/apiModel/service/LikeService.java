package es.natalia.natminds.apiModel.service;

import es.natalia.natminds.model.model.Post;
import org.codehaus.plexus.resource.loader.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LikeService {
  public void likePost(Long userId, Long postId);

  Long getLikeCountForPost(Post post);

  public boolean userLikedPost(Long userId, Long postId);
}
