package br.com.projects.domain.business.publico.commentlike;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.exceptions.ForbiddenException;
import br.com.projects.domain.business.exceptions.UniqueConstraintViolationException;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.authservice.api.AuthService;
import br.com.projects.domain.business.publico.commentlike.api.CommentLikeService;
import br.com.projects.domain.business.publico.commentlike.spi.CrudCommentLike;
import br.com.projects.domain.business.publico.user.DUser;

import java.util.Optional;

@DomainService
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CrudCommentLike crudCommentLike;
    private final AuthService authService;

    public CommentLikeServiceImpl(CrudCommentLike crudCommentLike, AuthService authService) {
        this.crudCommentLike = crudCommentLike;
        this.authService = authService;
    }

    @Override
    public Paged<DCommentLike> find(PageableRequest request) {
        return crudCommentLike.findAll(request);
    }

    @Override
    public DCommentLike find(Integer id) {
        return crudCommentLike.find(id);
    }

    @Override
    public DCommentLike insert(DCommentLike domain) {
        DUser authenticated = authService.authenticated();
        if(!domain.getUser().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode inserir uma curtida com o próprio Usuário.");
        }
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudCommentLike.insert(domain);
    }

    @Override
    public void delete(Integer id) {
        DCommentLike domain = find(id);
        DUser authenticated = authService.authenticated();
        if(!domain.getUser().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode excluir uma curtida com o próprio Usuário.");
        }
        crudCommentLike.delete(id);
    }

    private void validarRegistroDuplicado(DCommentLike domain) {
        if(crudCommentLike.findByCommentAndUser(domain.getComment().getId(), domain.getUser().getId())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Comentário e Usuário.");
        }
    }
}
