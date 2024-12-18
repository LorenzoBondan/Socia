package br.com.projects.persistence.publico.follow;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Follow;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@QueryService
public interface FollowQueryRepository extends PagingAndSortingRepository<Follow, Integer>, JpaSpecificationExecutor<Follow> {

    Collection<Follow> findByUserFollower_IdAndUserFollowing_Id(Integer userFollowerId, Integer userFollowingId); // usado para verificar registro duplicado
}
