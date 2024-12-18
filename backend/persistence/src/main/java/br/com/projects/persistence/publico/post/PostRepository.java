package br.com.projects.persistence.publico.post;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface PostRepository extends JpaRepository<Post, Integer> {
}
