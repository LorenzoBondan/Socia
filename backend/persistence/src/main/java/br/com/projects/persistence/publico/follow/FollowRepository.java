package br.com.projects.persistence.publico.follow;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface FollowRepository extends JpaRepository<Follow, Integer> {
}
