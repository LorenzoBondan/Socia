package br.com.projects.domain.business.publico.user;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.exceptions.RegistroDuplicadoException;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.authservice.api.AuthService;
import br.com.projects.domain.business.publico.user.api.UserService;
import br.com.projects.domain.business.publico.user.spi.CrudUser;

import java.util.Optional;

@DomainService
public class UserServiceImpl implements UserService {

    private final CrudUser crudUser;
    private final AuthService authService;

    public UserServiceImpl(CrudUser crudUser, AuthService authService) {
        this.crudUser = crudUser;
        this.authService = authService;
    }

    @Override
    public DUser find(Integer id) {
        authService.validateSelfOrAdmin(Long.valueOf(id));
        return crudUser.find(id);
    }

    @Override
    public DUser find(String email) {
        DUser user = crudUser.findByEmail(email).stream().findFirst().orElse(null);
        if(user != null) {
            authService.validateSelfOrAdmin(Long.valueOf(user.getId()));
            return user;
        }
        return null;
    }

    @Override
    public Paged<DUser> find(PageableRequest request) {
        return crudUser.findAll(request);
    }

    @Override
    public DUser incluir(DUser domain) {
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUser.insert(domain);
    }

    @Override
    public DUser update(DUser domain) {
        authService.validateSelfOrAdmin(Long.valueOf(domain.getId()));
        domain.validate();
        validateDuplicatedResource(domain);
        return crudUser.update(domain);
    }

    @Override
    public void updatePassword(String newPassword, String oldPassword) {
        DUser user = authService.authenticated();
        authService.validateSelfOrAdmin(Long.valueOf(user.getId()));
        crudUser.updatePassword(newPassword, oldPassword, user);
    }

    @Override
    public void delete(Integer id) {
        DUser user = authService.authenticated();
        authService.validateSelfOrAdmin(Long.valueOf(user.getId()));
        crudUser.delete(id);
    }

    private void validateDuplicatedResource(DUser domain){
        if(crudUser.findByEmail(domain.getEmail())
                .stream()
                .anyMatch(t -> !t.getId().equals(Optional.ofNullable(domain.getId()).orElse(-1)))){
            throw new RegistroDuplicadoException("Verifique o campo email.");
        }
    }
}
