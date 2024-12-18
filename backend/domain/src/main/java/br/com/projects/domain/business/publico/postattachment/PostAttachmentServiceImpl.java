package br.com.projects.domain.business.publico.postattachment;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.exceptions.ForbiddenException;
import br.com.projects.domain.business.exceptions.UniqueConstraintViolationException;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.authservice.api.AuthService;
import br.com.projects.domain.business.publico.postattachment.api.PostAttachmentService;
import br.com.projects.domain.business.publico.postattachment.spi.CrudPostAttachment;
import br.com.projects.domain.business.publico.user.DUser;

import java.util.Optional;

@DomainService
public class PostAttachmentServiceImpl implements PostAttachmentService {

    private final CrudPostAttachment crudPostAttachment;
    private final AuthService authService;

    public PostAttachmentServiceImpl(CrudPostAttachment crudPostAttachment, AuthService authService) {
        this.crudPostAttachment = crudPostAttachment;
        this.authService = authService;
    }

    @Override
    public Paged<DPostAttachment> find(PageableRequest request) {
        return crudPostAttachment.findAll(request);
    }

    @Override
    public DPostAttachment find(Integer id) {
        return crudPostAttachment.find(id);
    }

    @Override
    public DPostAttachment insert(DPostAttachment domain) {
        DUser authenticated = authService.authenticated();
        if(!domain.getPost().getUserAuthor().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode postar com o próprio Usuário.");
        }
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudPostAttachment.insert(domain);
    }

    @Override
    public DPostAttachment update(DPostAttachment domain) {
        DUser authenticated = authService.authenticated();
        if(!domain.getPost().getUserAuthor().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode atualizar uma publicação do próprio Usuário.");
        }
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudPostAttachment.update(domain);
    }

    @Override
    public void delete(Integer id) {
        DPostAttachment domain = crudPostAttachment.find(id);
        DUser authenticated = authService.authenticated();
        if(!domain.getPost().getUserAuthor().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode excluir uma própria publicação.");
        }
        crudPostAttachment.delete(id);
    }

    private void validarRegistroDuplicado(DPostAttachment domain) {
        if(crudPostAttachment.findByPostAndAttachment(domain.getPost().getId(), domain.getAttachment().getId())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Publicação e Anexo.");
        }
    }
}
