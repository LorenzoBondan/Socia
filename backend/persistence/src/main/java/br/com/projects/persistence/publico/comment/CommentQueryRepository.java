package br.com.projects.persistence.publico.comment;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Comment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@QueryService
public interface CommentQueryRepository extends PagingAndSortingRepository<Comment, Integer>, JpaSpecificationExecutor<Comment> {
}
