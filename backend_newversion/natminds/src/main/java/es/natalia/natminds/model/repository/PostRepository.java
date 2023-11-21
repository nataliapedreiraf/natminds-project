package es.natalia.natminds.model.repository;

import es.natalia.natminds.model.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository
        extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {
  List<Post> findByUserUserId(Long userId);
}
