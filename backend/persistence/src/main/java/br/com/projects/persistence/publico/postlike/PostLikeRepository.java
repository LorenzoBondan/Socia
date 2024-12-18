package br.com.projects.persistence.publico.postlike;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {
}
