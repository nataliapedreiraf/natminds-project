package es.natalia.natminds.apiRest.rest;

import es.natalia.natminds.apiModel.service.PostService;
import es.natalia.natminds.apiRest.dto.PostDto;
import es.natalia.natminds.model.model.Post;
import es.natalia.natminds.model.model.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
@CrossOrigin
@RestController
public class PostController {
    @Autowired
    private PostService postService;
    private Post post;

    // POST
    @PostMapping("/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto) {
        Post post = new Post();
        User user = new User();
        user.setUserId(postDto.getUserId());
        post.setUser(user);
        post.setText(postDto.getText());

        postService.createPost(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);

        PostDto postDto = new PostDto();
        postDto.setPostId(post.getPostId());
        postDto.setUserId(post.getUser().getUserId());
        postDto.setText(post.getText());


        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<PostDto> removePost(@PathVariable Long postId) {
        postService.removePost((postId));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/posts/user/{userId}")
    public ResponseEntity<List<PostDto>> getPostsByUserId(@PathVariable Long userId) {
        try {
            // Obtener la lista de posts asociados al usuario mediante el userId
            List<Post> userPosts = postService.findByUserUserId(userId);

            // Verificar si la lista de posts es vacía
            if (userPosts.isEmpty()) {
                return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND); // Devolver 404 si no hay posts para ese usuario
            }

            // Crear una lista de PostDto para almacenar los resultados
            List<PostDto> postDtos = new ArrayList<>();

            // Convertir cada Post a PostDto y agregarlo a la lista
            for (Post post : userPosts) {
                PostDto postDto = new PostDto();
                postDto.setPostId(post.getPostId());

                // Ajustar el nombre del atributo según la relación en la entidad Post
                if (post.getUser() != null) {
                    postDto.setUserId(post.getUser().getUserId());
                }

                postDto.setText(post.getText());
                postDtos.add(postDto);
            }

            // Devolver la lista de PostDto en la respuesta
            return new ResponseEntity<>(postDtos, HttpStatus.OK);
        } catch (Exception e) {
            // Manejar la excepción de manera adecuada (por ejemplo, devolver un error 500)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/authuser/")
    public ResponseEntity<List<PostDto>> getPostsByAuthUserId(HttpSession httpSession) {
        try {

            Long userId = (Long) httpSession.getAttribute("userId");
            if (userId == null)
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            // Obtener la lista de posts asociados al usuario mediante el userId
            List<Post> userPosts = postService.findByUserUserId(userId);

            // Verificar si la lista de posts es vacía
            if (userPosts.isEmpty()) {
                return new ResponseEntity<>(
                    HttpStatus.NOT_FOUND); // Devolver 404 si no hay posts para ese usuario
            }

            // Crear una lista de PostDto para almacenar los resultados
            List<PostDto> postDtos = new ArrayList<>();

            // Convertir cada Post a PostDto y agregarlo a la lista
            for (Post post : userPosts) {
                PostDto postDto = new PostDto();
                postDto.setPostId(post.getPostId());

                // Ajustar el nombre del atributo según la relación en la entidad Post
                if (post.getUser() != null) {
                    postDto.setUserId(post.getUser().getUserId());
                }

                postDto.setText(post.getText());
                postDtos.add(postDto);
            }

            // Devolver la lista de PostDto en la respuesta
            return new ResponseEntity<>(postDtos, HttpStatus.OK);
        } catch (Exception e) {
            // Manejar la excepción de manera adecuada (por ejemplo, devolver un error 500)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/all")
    public List<Post> getAllPosts() {
        return postService.findAll();
    }

    @GetMapping("/posts/allWithLikeCount")
    public ResponseEntity<List<PostDto>> getAllPostsWithLikeCount() {
        try {
            List<Object[]> postsWithLikeCount = postService.findAllPostsWithLikeCount();

            List<PostDto> postDtos = new ArrayList<>();

            for (Object[] postWithLikeCount : postsWithLikeCount) {
                Post post = (Post) postWithLikeCount[0];
                Long likeCount = (Long) postWithLikeCount[1];

                PostDto postDto = new PostDto();
                postDto.setPostId(post.getPostId());
                // Otros campos del postDto
                postDto.setLikeCount(likeCount);

                postDtos.add(postDto);
            }

            return new ResponseEntity<>(postDtos, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
