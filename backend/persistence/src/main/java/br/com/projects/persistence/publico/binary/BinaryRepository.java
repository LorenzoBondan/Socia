package br.com.projects.persistence.publico.binary;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Binary;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface BinaryRepository extends JpaRepository<Binary, Integer> {
}
