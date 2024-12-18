package br.com.projects.persistence.publico.postattachment;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.PostAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

@QueryService
public interface PostAttachmentRepository extends JpaRepository<PostAttachment, Integer> {
}
