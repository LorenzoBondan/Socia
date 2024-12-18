package br.com.projects.domain.business.publico.follow;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.exceptions.ForbiddenException;
import br.com.projects.domain.business.exceptions.UniqueConstraintViolationException;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.authservice.api.AuthService;
import br.com.projects.domain.business.publico.follow.api.FollowService;
import br.com.projects.domain.business.publico.follow.spi.CrudFollow;
import br.com.projects.domain.business.publico.user.DUser;

import java.util.Optional;

@DomainService
public class FollowServiceImpl implements FollowService {

    private final CrudFollow crudFollow;
    private final AuthService authService;

    public FollowServiceImpl(CrudFollow crudFollow, AuthService authService) {
        this.crudFollow = crudFollow;
        this.authService = authService;
    }

    @Override
    public Paged<DFollow> find(PageableRequest request) {
        return crudFollow.findAll(request);
    }

    @Override
    public DFollow find(Integer id) {
        return crudFollow.find(id);
    }

    @Override
    public DFollow insert(DFollow domain) {
        DUser authenticated = authService.authenticated();
        if(!domain.getUserFollower().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode seguir com o próprio Usuário.");
        }
        domain.validate();
        validarRegistroDuplicado(domain);
        return crudFollow.insert(domain);
    }

    @Override
    public void delete(Integer id) {
        DFollow domain = find(id);
        DUser authenticated = authService.authenticated();
        if(!domain.getUserFollower().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode deixar de seguir com o próprio Usuário.");
        }
        crudFollow.delete(id);
    }

    private void validarRegistroDuplicado(DFollow domain) {
        if(crudFollow.findByUserFollowerAndUserFollowing(domain.getUserFollower().getId(), domain.getUserFollowing().getId())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1)))){
            throw new UniqueConstraintViolationException("Registro duplicado para a combinação de Usuário Seguidor e Usuário Seguindo.");
        }
    }
}
