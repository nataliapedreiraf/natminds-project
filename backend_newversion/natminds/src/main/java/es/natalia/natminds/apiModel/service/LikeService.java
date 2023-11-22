package es.natalia.natminds.apiModel.service;

import org.codehaus.plexus.resource.loader.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LikeService {
  public void likePost(Long userId, Long postId);
}
