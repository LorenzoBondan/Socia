package br.com.projects.persistence.publico.comment;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
