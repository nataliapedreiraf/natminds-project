package es.natalia.natminds.apiRest.rest;

import es.natalia.natminds.apiRest.dto.LikeDto;
import es.natalia.natminds.apiModel.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
public class LikeController {

  @Autowired
  private LikeService likeService;

  @PostMapping("/like")
  public ResponseEntity<String> likePost(@RequestBody LikeDto likeDto) {
    // Implementa la lógica para dar like al post
    likeService.likePost(likeDto.getUserId(), likeDto.getPostId());

    return ResponseEntity.ok("Like successful");
  }
}
