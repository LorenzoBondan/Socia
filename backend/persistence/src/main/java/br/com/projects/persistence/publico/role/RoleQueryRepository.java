package br.com.projects.persistence.publico.role;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@QueryService
public interface RoleQueryRepository extends PagingAndSortingRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
}