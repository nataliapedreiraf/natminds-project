package es.natalia.natminds.apiRest.rest;

import es.natalia.natminds.apiModel.service.LikeService;
import es.natalia.natminds.apiModel.service.PostService;
import es.natalia.natminds.model.model.Post;
import es.natalia.natminds.model.model.User;
import es.natalia.natminds.model.repository.PostRepository;
import es.natalia.natminds.model.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** Controlador REST para gestionar operaciones relacionadas con "likes" en publicaciones. */
@RestController
@RequestMapping("/likes")
public class LikeController {

  @Autowired private LikeService likeService;

  @Autowired private UserRepository userRepository;

  @Autowired private PostRepository postRepository;

  @Autowired PostService postService;

  /**
   * Maneja la solicitud para dar "like" a una publicación.
   *
   * @param httpSession La sesión HTTP que contiene la información del usuario.
   * @param postId El ID de la publicación a la que se le dará "like".
   * @return ResponseEntity con el recuento actualizado de "likes" para la publicación.
   */
  @PostMapping("/like")
  public ResponseEntity<Long> likePost(HttpSession httpSession, @RequestParam Long postId) {
    Long userId = (Long) httpSession.getAttribute("userId");

    System.out.println("User ID from session: " + userId);

    if (userId == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    try {

      if (!likeService.userLikedPost(userId, postId)) {
        likeService.likePost(userId, postId);
      }

      Long updatedLikesCount = postService.getLikeCountForPost(postId);
      return ResponseEntity.ok(updatedLikesCount);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Maneja la solicitud para quitar el "like" de una publicación.
   *
   * @param httpSession La sesión HTTP que contiene la información del usuario.
   * @param postId El ID de la publicación de la cual se quitará el "like".
   * @return ResponseEntity con un mensaje indicando que el "like" se quitó exitosamente.
   */
  @PostMapping("/unlike")
  public ResponseEntity<String> unlikePost(HttpSession httpSession, @RequestParam Long postId) {
    Long userId = (Long) httpSession.getAttribute("userId");

    System.out.println("User ID from session: " + userId);

    if (userId == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    try {
      User user =
          userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

      Post post =
          postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Post not found"));

      likeService.unlikePost(user, post);
      return ResponseEntity.ok("Unliked post successfully");
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
