package br.com.projects.persistence.publico.post;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Post;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@QueryService
public interface PostQueryRepository extends PagingAndSortingRepository<Post, Integer>, JpaSpecificationExecutor<Post> {
}
