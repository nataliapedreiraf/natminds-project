package es.natalia.natminds.model.service;

import es.natalia.natminds.apiModel.service.LikeService;
import es.natalia.natminds.apiModel.service.PostService;
import es.natalia.natminds.model.model.Post;
import es.natalia.natminds.model.model.User;
import es.natalia.natminds.model.repository.LikeRepository;
import es.natalia.natminds.model.repository.PostRepository;
import es.natalia.natminds.model.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Implementación del servicio de publicaciones (posts). */
@Service
@Transactional
public class PostServiceImpl implements PostService {

  @Autowired PostRepository postRepository;

  @Autowired UserRepository userRepository;

  @Autowired LikeService likeService;

  @Autowired private LikeRepository likeRepository;

  /**
   * Obtiene una publicación por su ID.
   *
   * @param postId ID de la publicación.
   * @return La publicación correspondiente al ID.
   */
  @Override
  public Post getPost(Long postId) {
    Optional<Post> postEntity = postRepository.findById(postId);
    return postEntity.orElseThrow(
        () -> new EntityNotFoundException("Post not found with ID: " + postId));
  }

  /**
   * Crea una nueva publicación.
   *
   * @param post Datos de la nueva publicación.
   * @return La publicación creada.
   */
  @Override
  public Post createPost(Post post) {
    Post newPost = new Post();
    newPost.setText(post.getText());
    Optional<User> user = userRepository.findById(post.getUser().getUserId());
    newPost.setUser(user.orElseThrow(() -> new EntityNotFoundException("User not found")));

    return postRepository.save(newPost);
  }

  /**
   * Elimina una publicación por su ID.
   *
   * @param postId ID de la publicación a eliminar.
   */
  @Override
  public void removePost(Long postId) {
    postRepository.deleteById(postId);
  }

  /**
   * Obtiene la lista de publicaciones asociadas a un usuario por su ID.
   *
   * @param userId ID del usuario.
   * @return Lista de publicaciones del usuario.
   */
  @Override
  public List<Post> findByUserUserId(Long userId) {
    return postRepository.findByUserUserId(userId);
  }

  /**
   * Obtiene la lista de todas las publicaciones.
   *
   * @return Lista de todas las publicaciones.
   */
  @Override
  public List<Post> findAll() {
    return postRepository.findAll();
  }

  /**
   * Obtiene la lista de todas las publicaciones con la cuenta de "likes".
   *
   * @return Lista de publicaciones con la cuenta de "likes".
   */
  @Override
  public List<Object[]> findAllPostsWithLikeCount() {
    return likeRepository.findAllPostsAndLikeCount();
  }

  /**
   * Obtiene la cantidad de "likes" para una publicación por su ID.
   *
   * @param postId ID de la publicación.
   * @return Cantidad de "likes" para la publicación.
   */
  @Override
  public Long getLikeCountForPost(Long postId) {
    Post post = postRepository.findById(postId).orElse(null);

    if (post != null) {
      return likeService.getLikeCountForPost(post);
    } else {
      throw new EntityNotFoundException("Post not found with ID: " + postId);
    }
  }
}
