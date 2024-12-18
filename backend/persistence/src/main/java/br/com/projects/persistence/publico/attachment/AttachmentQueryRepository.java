package br.com.projects.persistence.publico.attachment;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Attachment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

@QueryService
public interface AttachmentQueryRepository extends PagingAndSortingRepository<Attachment, Integer>, JpaSpecificationExecutor<Attachment> {
}
