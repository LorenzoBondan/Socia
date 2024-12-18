package br.com.projects.domain.business.publico.postlike;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.exceptions.ForbiddenException;
import br.com.projects.domain.business.exceptions.UniqueConstraintViolationException;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.authservice.api.AuthService;
import br.com.projects.domain.business.publico.postlike.api.PostLikeService;
import br.com.projects.domain.business.publico.postlike.spi.CrudPostLike;
import br.com.projects.domain.business.publico.user.DUser;

import java.util.Optional;

@DomainService
public class PostLikeServiceImpl implements PostLikeService {

    private final CrudPostLike crudPostLike;
    private final AuthService authService;

    public PostLikeServiceImpl(CrudPostLike crudPostLike, AuthService authService) {
        this.crudPostLike = crudPostLike;
        this.authService = authService;
    }

    @Override
    public Paged<DPostLike> find(PageableRequest request) {
        return crudPostLike.findAll(request);
    }

    @Override
    public DPostLike find(Integer id) {
        return crudPostLike.find(id);
    }

    @Override
    public DPostLike insert(DPostLike domain) {
        DUser authenticated = authService.authenticated();
        if(!domain.getPost().getUserAuthor().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode curtir uma publicação com o próprio Usuário.");
        }
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudPostLike.insert(domain);
    }

    @Override
    public void delete(Integer id) {
        DPostLike domain = crudPostLike.find(id);
        DUser authenticated = authService.authenticated();
        if(!domain.getPost().getUserAuthor().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode excluir uma curtida de uma publicação com o próprio Usuário.");
        }
        crudPostLike.delete(id);
    }

    private void validarRegistroDuplicado(DPostLike domain) {
        if(crudPostLike.findByPostAndUser(domain.getPost().getId(), domain.getUser().getId())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Publicação e Usuário.");
        }
    }
}
