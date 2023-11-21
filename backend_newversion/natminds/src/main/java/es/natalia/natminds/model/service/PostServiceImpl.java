package es.natalia.natminds.model.service;


import es.natalia.natminds.apiModel.service.PostService;

import es.natalia.natminds.model.model.Post;
import es.natalia.natminds.model.repository.PostRepository;
import es.natalia.natminds.model.model.User;
import es.natalia.natminds.model.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    private Post post;
    
    @Override
    public Post getPost(Long postId) {
        Optional<Post> postEntity = postRepository.findById(postId);
        return postEntity.get();
    }


    @Override
    public Post createPost(Post post) {
        Post post2 = new Post();
        post2.setText(post.getText());
        Optional<User> user = userRepository.findById(post.getUser().getUserId());
        post2.setUser(user.orElseThrow());

        return postRepository.save(post2);
    }

    @Override
    public void removePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public List<Post> findByUserUserId(Long userId) {
        return postRepository.findByUserUserId(userId);
    }

    @Override
    public List<Post> findAll() {return postRepository.findAll();}

}
