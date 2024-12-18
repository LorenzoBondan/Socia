package br.com.projects.persistence.publico.attachment;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.attachment.DAttachment;
import br.com.projects.persistence.entities.Attachment;
import br.com.projects.persistence.entities.Binary;
import br.com.projects.persistence.publico.binary.BinaryDomainToEntityAdapter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AttachmentDomainToEntityAdapter implements Convertable<Attachment, DAttachment> {

    private final BinaryDomainToEntityAdapter binaryDomainToEntityAdapter;

    public AttachmentDomainToEntityAdapter(BinaryDomainToEntityAdapter binaryDomainToEntityAdapter) {
        this.binaryDomainToEntityAdapter = binaryDomainToEntityAdapter;
    }

    @Override
    public Attachment toEntity(DAttachment domain) {
        return Attachment.builder()
                .id(domain.getId())
                .binary(new Binary(domain.getBinary().getId()))
                .name(domain.getName())
                .mimeType(domain.getMimeType())
                .url(domain.getUrl())
                .checksum(domain.getChecksum())
                .build();
    }

    @Override
    public DAttachment toDomain(Attachment entity) {
        return DAttachment.builder()
                .id(entity.getId())
                .binary(Optional.ofNullable(entity.getBinary())
                        .map(binaryDomainToEntityAdapter::toDomain)
                        .orElse(null))
                .name(entity.getName())
                .mimeType(entity.getMimeType())
                .url(entity.getUrl())
                .checksum(entity.getChecksum())
                .build();
    }
}
