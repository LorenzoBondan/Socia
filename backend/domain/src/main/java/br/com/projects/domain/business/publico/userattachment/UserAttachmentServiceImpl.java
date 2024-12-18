package br.com.projects.domain.business.publico.userattachment;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.exceptions.UniqueConstraintViolationException;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.userattachment.api.UserAttachmentService;
import br.com.projects.domain.business.publico.userattachment.spi.CrudUserAttachment;

import java.util.Optional;

@DomainService
public class UserAttachmentServiceImpl implements UserAttachmentService {

    private final CrudUserAttachment crudUserAttachment;

    public UserAttachmentServiceImpl(CrudUserAttachment crudUserAttachment) {
        this.crudUserAttachment = crudUserAttachment;
    }

    @Override
    public Paged<DUserAttachment> find(PageableRequest request) {
        return crudUserAttachment.findAll(request);
    }

    @Override
    public DUserAttachment find(Integer id) {
        return crudUserAttachment.find(id);
    }

    @Override
    public DUserAttachment insert(DUserAttachment domain) {
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudUserAttachment.insert(domain);
    }

    @Override
    public DUserAttachment update(DUserAttachment domain) {
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudUserAttachment.update(domain);
    }

    @Override
    public void delete(Integer id) {
        crudUserAttachment.delete(id);
    }

    private void validarRegistroDuplicado(DUserAttachment domain) {
        if(crudUserAttachment.findByUserAndAttachment(domain.getUser().getId(), domain.getAttachment().getId())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Usuário e Anexo.");
        }
    }
}
