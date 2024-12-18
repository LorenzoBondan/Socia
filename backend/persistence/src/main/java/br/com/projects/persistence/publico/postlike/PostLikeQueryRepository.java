package br.com.projects.persistence.publico.postlike;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.PostLike;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@QueryService
public interface PostLikeQueryRepository extends PagingAndSortingRepository<PostLike, Integer>, JpaSpecificationExecutor<PostLike> {

    Collection<PostLike> findByPost_IdAndUser_Id(Integer postId, Integer userId); // usado para verificar registro duplicado
}
