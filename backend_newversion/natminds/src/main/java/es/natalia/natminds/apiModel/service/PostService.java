package es.natalia.natminds.apiModel.service;

import es.natalia.natminds.apiRest.dto.PostDto;
import es.natalia.natminds.model.model.Post;
import java.util.List;

public interface PostService {
    public Post getPost(Long postId);
    public Post createPost(Post post);
    public void removePost(Long postId);
    List<Post> findByUserUserId(Long userId);
    public List<Post> findAll();
}
