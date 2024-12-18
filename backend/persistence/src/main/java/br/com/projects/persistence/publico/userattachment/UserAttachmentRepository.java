package br.com.projects.persistence.publico.userattachment;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.UserAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface UserAttachmentRepository extends JpaRepository<UserAttachment, Integer> {
}
