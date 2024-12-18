package br.com.projects.domain.business.publico.authservice;

import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.authservice.api.AuthService;
import br.com.projects.domain.business.publico.authservice.spi.AuthMethods;
import br.com.projects.domain.business.publico.user.DUser;

@DomainService
public class AuthServiceImpl implements AuthService {

    private final AuthMethods authMethods;

    public AuthServiceImpl(AuthMethods authMethods) {
        this.authMethods = authMethods;
    }

    @Override
    public DUser authenticated() {
        return authMethods.authenticated();
    }

    @Override
    public void validateSelfOrAdmin(Long userId) {
        authMethods.validateSelfOrAdmin(userId);
    }
}
