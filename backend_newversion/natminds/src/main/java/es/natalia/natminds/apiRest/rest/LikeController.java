package es.natalia.natminds.apiRest.rest;

import es.natalia.natminds.apiRest.dto.LikeDto;
import es.natalia.natminds.apiModel.service.LikeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikeController {

  @Autowired
  private LikeService likeService;

  @PostMapping("/like")
  public ResponseEntity<String> likePost(HttpSession httpSession, @RequestParam Long postId) {

    Long userId = (Long) httpSession.getAttribute("userId");

    if (userId == null) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    try {
      likeService.likePost(userId, postId);
      return ResponseEntity.ok("Like successful");
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
