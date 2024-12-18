package br.com.projects.persistence.publico.attachment;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
