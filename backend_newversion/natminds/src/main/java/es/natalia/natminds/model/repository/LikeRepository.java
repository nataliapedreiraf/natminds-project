package es.natalia.natminds.model.repository;

import es.natalia.natminds.model.model.Like;
import es.natalia.natminds.model.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long>, JpaSpecificationExecutor<Like> {

  @Query("SELECT p, COUNT(l) AS likeCount FROM Post p LEFT JOIN Like l ON p.postId = l.post.postId GROUP BY p")
  List<Object[]> findAllPostsAndLikeCount();

  Long countByPost(Post post);
}
