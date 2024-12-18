package br.com.projects.persistence.publico.role;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface RoleRepository extends JpaRepository<Role, Integer> {
}
