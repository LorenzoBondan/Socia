package br.com.projects.persistence.publico.userattachment;

import br.com.projects.domain.business.metadata.QueryService;
import br.com.projects.persistence.entities.UserAttachment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

@QueryService
public interface UserAttachmentQueryRepository extends PagingAndSortingRepository<UserAttachment, Integer>, JpaSpecificationExecutor<UserAttachment> {

    Collection<UserAttachment> findByUser_IdAndAttachment_Id(Integer userId, Integer attachmentId); // usado para verificar registro duplicado
}
