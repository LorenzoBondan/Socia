package br.com.projects.domain.business.publico.attachment;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.attachment.api.AttachmentService;
import br.com.projects.domain.business.publico.attachment.spi.CrudAttachment;

@DomainService
public class AttachmentServiceImpl implements AttachmentService {

    private final CrudAttachment crudAttachment;

    public AttachmentServiceImpl(CrudAttachment crudAttachment) {
        this.crudAttachment = crudAttachment;
    }

    @Override
    public Paged<DAttachment> find(PageableRequest request) {
        return crudAttachment.findAll(request);
    }

    @Override
    public DAttachment find(Integer id) {
        return crudAttachment.find(id);
    }

    @Override
    public DAttachment insert(DAttachment domain) {
        domain.validate();
        return crudAttachment.insert(domain);
    }

    @Override
    public DAttachment update(DAttachment domain) {
        domain.validate();
        return crudAttachment.update(domain);
    }

    @Override
    public void delete(Integer id) {
        crudAttachment.delete(id);
    }
}
