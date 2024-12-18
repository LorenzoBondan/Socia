package br.com.projects.persistence.publico.commentlike;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.CommentLike;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@QueryService
public interface CommentLikeQueryRepository extends PagingAndSortingRepository<CommentLike, Integer>, JpaSpecificationExecutor<CommentLike> {

    Collection<CommentLike> findByComment_IdAndUser_Id(Integer commentId, Integer userId); // usado para verificar registro duplicado
}
