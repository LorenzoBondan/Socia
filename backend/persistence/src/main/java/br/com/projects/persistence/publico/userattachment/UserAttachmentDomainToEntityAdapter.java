package br.com.projects.persistence.publico.userattachment;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.domain.business.publico.userattachment.DUserAttachment;
import br.com.projects.persistence.entities.Attachment;
import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.entities.UserAttachment;
import br.com.projects.persistence.publico.attachment.AttachmentDomainToEntityAdapter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAttachmentDomainToEntityAdapter implements Convertable<UserAttachment, DUserAttachment> {
    
    private final AttachmentDomainToEntityAdapter attachmentDomainToEntityAdapter;

    public UserAttachmentDomainToEntityAdapter(AttachmentDomainToEntityAdapter attachmentDomainToEntityAdapter) {
        this.attachmentDomainToEntityAdapter = attachmentDomainToEntityAdapter;
    }

    @Override
    public UserAttachment toEntity(DUserAttachment domain) {
        return UserAttachment.builder()
                .id(domain.getId())
                .user(new User(domain.getUser().getId()))
                .attachment(new Attachment(domain.getAttachment().getId()))
                .build();
    }

    @Override
    public DUserAttachment toDomain(UserAttachment entity) {

        return DUserAttachment.builder()
                .id(entity.getId())
                .user(Optional.ofNullable(entity.getUser())
                        .map(User -> new DUser(User.getId()))
                        .orElse(null))
                .attachment(Optional.ofNullable(entity.getAttachment())
                        .map(attachmentDomainToEntityAdapter::toDomain)
                        .orElse(null))
                .build();
    }
}
