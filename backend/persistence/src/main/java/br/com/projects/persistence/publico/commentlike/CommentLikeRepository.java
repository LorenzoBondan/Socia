package br.com.projects.persistence.publico.commentlike;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface CommentLikeRepository extends JpaRepository<CommentLike, Integer> {
}
