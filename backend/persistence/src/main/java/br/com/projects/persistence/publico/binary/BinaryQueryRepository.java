package br.com.projects.persistence.publico.binary;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Binary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@QueryService
public interface BinaryQueryRepository extends PagingAndSortingRepository<Binary, Integer>, JpaSpecificationExecutor<Binary> {
}
