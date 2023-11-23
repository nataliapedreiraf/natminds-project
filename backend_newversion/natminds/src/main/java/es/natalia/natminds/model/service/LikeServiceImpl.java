package es.natalia.natminds.model.service;

import es.natalia.natminds.apiModel.service.LikeService;
import es.natalia.natminds.model.model.Like;
import es.natalia.natminds.model.model.Post;
import es.natalia.natminds.model.model.User;
import es.natalia.natminds.model.repository.LikeRepository;
import es.natalia.natminds.model.repository.PostRepository;
import es.natalia.natminds.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

  @Autowired
  private LikeRepository likeRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PostRepository postRepository;

  @Override
  public void likePost(Long userId, Long postId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found"));

    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new RuntimeException("Post not found"));

    Like like = new Like();
    like.setUser(user);
    like.setPost(post);

    likeRepository.save(like);
  }

  @Override
  public Long getLikeCountForPost(Post post) {
    // Implementa la lógica para contar los likes de un post específico
    return likeRepository.countByPost(post);
  }
}
