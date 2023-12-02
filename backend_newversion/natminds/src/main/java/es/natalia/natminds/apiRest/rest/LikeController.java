package es.natalia.natminds.apiRest.rest;

import es.natalia.natminds.apiModel.service.PostService;
import es.natalia.natminds.apiModel.service.LikeService;
import es.natalia.natminds.model.model.Post;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/likes")
public class LikeController {

  @Autowired
  private LikeService likeService;

  @Autowired
  PostService postService;

  /*@PostMapping("/like")
  public ResponseEntity<Long> likePost(HttpSession httpSession, @RequestParam Long postId) {
    Long userId = (Long) httpSession.getAttribute("userId");

    System.out.println("User ID from session: " + userId);

    if (userId == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    try {
      likeService.likePost(userId, postId);
      // Obtener el recuento de likes actualizado después de dar "Me gusta"
      Long updatedLikesCount = postService.getLikeCountForPost(postId);
      return ResponseEntity.ok(updatedLikesCount);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }*/

  @PostMapping("/like")
  public ResponseEntity<Long> likePost(HttpSession httpSession, @RequestParam Long postId) {
    Long userId = (Long) httpSession.getAttribute("userId");

    System.out.println("User ID from session: " + userId);

    if (userId == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    try {
      // Verificar si el usuario ya le dio "Me gusta" al post
      if (!likeService.userLikedPost(userId, postId)) {
        // El usuario no le ha dado "Me gusta" al post, procede a darle "Me gusta"
        likeService.likePost(userId, postId);
      }

      // Obtener el recuento de likes actualizado después de dar "Me gusta"
      Long updatedLikesCount = postService.getLikeCountForPost(postId);
      return ResponseEntity.ok(updatedLikesCount);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
