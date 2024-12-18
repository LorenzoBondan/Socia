package br.com.projects.persistence.publico.user;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@QueryService
public interface UserQueryRepository extends PagingAndSortingRepository<User, Integer>, JpaSpecificationExecutor<User> {
}
