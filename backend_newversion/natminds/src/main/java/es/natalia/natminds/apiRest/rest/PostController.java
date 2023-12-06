package es.natalia.natminds.apiRest.rest;

import es.natalia.natminds.apiModel.service.PostService;
import es.natalia.natminds.apiRest.dto.PostDto;
import es.natalia.natminds.apiRest.dto.PostLikesDto;
import es.natalia.natminds.model.model.Post;
import es.natalia.natminds.model.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Controlador REST para gestionar operaciones relacionadas con publicaciones (posts). */
@CrossOrigin
@RestController
public class PostController {

  @Autowired private PostService postService;
  private Post post;

  /**
   * Maneja la solicitud para crear una nueva publicación.
   *
   * @param postDto Datos de la nueva publicación.
   * @param httpSession La sesión HTTP que contiene la información del usuario.
   * @return ResponseEntity con el estado de la creación.
   */
  @PostMapping("/posts")
  public ResponseEntity<PostDto> createPost(
      @RequestBody @Valid PostDto postDto, HttpSession httpSession) {
    Post post = new Post();
    User user = new User();

    Long userId = (Long) httpSession.getAttribute("userId");
    user.setUserId(userId);
    post.setUser(user);
    post.setText(postDto.getText());

    postService.createPost(post);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Obtiene la información de una publicación por su ID.
   *
   * @param postId ID de la publicación.
   * @return ResponseEntity con la información de la publicación y el estado de la solicitud.
   */
  @GetMapping("/posts/{postId}")
  public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
    Post post = postService.getPost(postId);

    PostDto postDto = new PostDto();
    postDto.setPostId(post.getPostId());
    postDto.setUserId(post.getUser().getUserId());
    postDto.setText(post.getText());

    return new ResponseEntity<>(postDto, HttpStatus.OK);
  }

  /**
   * Elimina una publicación por su ID.
   *
   * @param postId ID de la publicación a eliminar.
   * @return ResponseEntity con el estado de la solicitud.
   */
  @DeleteMapping("/posts/{postId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<PostDto> removePost(@PathVariable Long postId) {
    postService.removePost((postId));
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  /**
   * Obtiene la lista de publicaciones asociadas a un usuario por su ID.
   *
   * @param userId ID del usuario.
   * @return ResponseEntity con la lista de publicaciones y el estado de la solicitud.
   */
  @GetMapping("/posts/user/{userId}")
  public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId) {
    try {
      List<Post> userPosts = postService.findByUserUserId(userId);

      if (userPosts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      List<PostDto> postDtos = new ArrayList<>();

      for (Post post : userPosts) {
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());

        if (post.getUser() != null) {
          postDto.setUserId(post.getUser().getUserId());
        }

        postDto.setText(post.getText());
        postDtos.add(postDto);
      }

      return new ResponseEntity<>(postDtos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Obtiene la lista de publicaciones asociadas al usuario autenticado.
   *
   * @param httpSession La sesión HTTP que contiene la información del usuario.
   * @return ResponseEntity con la lista de publicaciones y el estado de la solicitud.
   */
  @GetMapping("/posts/authuser/")
  public ResponseEntity<List<PostDto>> getPostsByAuthUserId(HttpSession httpSession) {
    try {
      Long userId = (Long) httpSession.getAttribute("userId");
      if (userId == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

      List<Post> userPosts = postService.findByUserUserId(userId);

      if (userPosts.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      }

      List<PostDto> postDtos = new ArrayList<>();

      for (Post post : userPosts) {
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());

        if (post.getUser() != null) {
          postDto.setUserId(post.getUser().getUserId());
        }

        postDto.setText(post.getText());
        postDtos.add(postDto);
      }

      return new ResponseEntity<>(postDtos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Obtiene la lista de todas las publicaciones.
   *
   * @return Lista de publicaciones.
   */
  @GetMapping("/posts/all")
  public List<PostDto> getAllPosts() {
    List<Post> posts = postService.findAll();
    List<PostDto> postDtos = new ArrayList<>();

    for (Post post : posts) {
      if (!Hibernate.isInitialized(post.getUser())) {
        Hibernate.initialize(post.getUser());
      }

      User user = post.getUser();
      postDtos.add(
          new PostDto(
              post.getPostId(),
              user.getUserId(),
              post.getText(),
              0L,
              user.getUserName(),
              user.getName() + " " + user.getLastName()));
    }

    return postDtos;
  }

  /**
   * Obtiene la lista de todas las publicaciones con la cuenta de "likes".
   *
   * @return ResponseEntity con la lista de publicaciones y el estado de la solicitud.
   */
  @GetMapping("/posts/allWithLikeCount")
  public ResponseEntity<List<PostLikesDto>> getAllPostsWithLikeCount() {
    try {
      List<Object[]> postsWithLikeCount = postService.findAllPostsWithLikeCount();

      List<PostLikesDto> postDtos = new ArrayList<>();

      for (Object[] postWithLikeCount : postsWithLikeCount) {
        Post post = (Post) postWithLikeCount[0];
        Long likeCount = (Long) postWithLikeCount[1];

        PostLikesDto postLikesDto = new PostLikesDto();
        postLikesDto.setPostId(post.getPostId());
        postLikesDto.setLikeCount(likeCount);

        postDtos.add(postLikesDto);
      }

      return new ResponseEntity<>(postDtos, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Obtiene la cantidad de "likes" para una publicación por su ID.
   *
   * @param postId ID de la publicación.
   * @return ResponseEntity con la cantidad de "likes" y el estado de la solicitud.
   */
  @GetMapping("/posts/{postId}/likeCount")
  public ResponseEntity<Long> getLikeCountForPost(@PathVariable Long postId) {
    try {
      Long likeCount = postService.getLikeCountForPost(postId);
      return new ResponseEntity<>(likeCount, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
