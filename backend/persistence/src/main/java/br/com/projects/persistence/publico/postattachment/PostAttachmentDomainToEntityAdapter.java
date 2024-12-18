package br.com.projects.persistence.publico.postattachment;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.post.DPost;
import br.com.projects.domain.business.publico.postattachment.DPostAttachment;
import br.com.projects.persistence.entities.Attachment;
import br.com.projects.persistence.entities.Post;
import br.com.projects.persistence.entities.PostAttachment;
import br.com.projects.persistence.publico.attachment.AttachmentDomainToEntityAdapter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostAttachmentDomainToEntityAdapter implements Convertable<PostAttachment, DPostAttachment> {

    private final AttachmentDomainToEntityAdapter attachmentDomainToEntityAdapter;

    public PostAttachmentDomainToEntityAdapter(AttachmentDomainToEntityAdapter attachmentDomainToEntityAdapter) {
        this.attachmentDomainToEntityAdapter = attachmentDomainToEntityAdapter;
    }

    @Override
    public PostAttachment toEntity(DPostAttachment domain) {
        return PostAttachment.builder()
                .id(domain.getId())
                .post(new Post(domain.getPost().getId()))
                .attachment(new Attachment(domain.getAttachment().getId()))
                .build();
    }

    @Override
    public DPostAttachment toDomain(PostAttachment entity) {

        return DPostAttachment.builder()
                .id(entity.getId())
                .post(Optional.ofNullable(entity.getPost())
                        .map(post -> new DPost(post.getId()))
                        .orElse(null))
                .attachment(Optional.ofNullable(entity.getAttachment())
                        .map(attachmentDomainToEntityAdapter::toDomain)
                        .orElse(null))
                .build();
    }
}
