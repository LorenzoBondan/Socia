package br.com.projects.persistence.publico.postattachment;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.PostAttachment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@QueryService
public interface PostAttachmentQueryRepository extends PagingAndSortingRepository<PostAttachment, Integer>, JpaSpecificationExecutor<PostAttachment> {

    Collection<PostAttachment> findByPost_IdAndAttachment_Id(Integer postId, Integer attachmentId); // usado para verificar registro duplicado
}
