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

/** Implementación del servicio de Likes. */
@Service
public class LikeServiceImpl implements LikeService {

  @Autowired private LikeRepository likeRepository;

  @Autowired private UserRepository userRepository;

  @Autowired private PostRepository postRepository;

  /**
   * Da un "like" a una publicación.
   *
   * @param userId ID del usuario que da el "like".
   * @param postId ID de la publicación a la que se da el "like".
   */
  @Override
  public void likePost(Long userId, Long postId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    Post post =
        postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

    Like like = new Like();
    like.setUser(user);
    like.setPost(post);

    if (!likeRepository.existsByUserAndPost(user, post)) {
      likeRepository.save(like);
    }
  }

  /**
   * Obtiene la cantidad de "likes" para una publicación.
   *
   * @param post Publicación.
   * @return Cantidad de "likes" para la publicación.
   */
  @Override
  public Long getLikeCountForPost(Post post) {
    return likeRepository.countByPost(post);
  }

  /**
   * Verifica si un usuario dio "like" a una publicación.
   *
   * @param userId ID del usuario.
   * @param postId ID de la publicación.
   * @return true si el usuario dio "like" a la publicación, false de lo contrario.
   */
  @Override
  public boolean userLikedPost(Long userId, Long postId) {
    User user =
        userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

    Post post =
        postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

    return likeRepository.existsByUserAndPost(user, post);
  }

  /**
   * Retira el "like" de un usuario a una publicación.
   *
   * @param user Usuario que retira el "like".
   * @param post Publicación.
   */
  @Override
  public void unlikePost(User user, Post post) {
    likeRepository.deleteByUserAndPost(user, post);
  }
}
