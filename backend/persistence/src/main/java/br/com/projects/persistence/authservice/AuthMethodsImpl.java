package br.com.projects.persistence.authservice;

import br.com.projects.domain.business.exceptions.ForbiddenException;
import br.com.projects.domain.business.publico.authservice.spi.AuthMethods;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.publico.user.UserDomainToEntityAdapter;
import br.com.projects.persistence.publico.user.UserRepository;
import br.com.projects.persistence.util.CustomUserUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AuthMethodsImpl implements AuthMethods {

    private final UserRepository repository;
    private final UserDomainToEntityAdapter adapter;
    private final CustomUserUtil customUserUtil;

    public AuthMethodsImpl(UserRepository repository, UserDomainToEntityAdapter adapter, CustomUserUtil customUserUtil) {
        this.repository = repository;
        this.adapter = adapter;
        this.customUserUtil = customUserUtil;
    }

    @Override
    public DUser authenticated() {
        try {
            String username = customUserUtil.getLoggedUsername();
            return adapter.toDomain(repository.findByEmail(username).iterator().next());
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("Invalid user");
        }
    }

    @Override
    public void validateSelfOrAdmin(Long userId) {
        User me = adapter.toEntity(authenticated());
        if (me.hasRole("ROLE_ADMIN")) {
            return;
        }
        throw new ForbiddenException("Acesso negado. Deve ser admin");
    }
}
