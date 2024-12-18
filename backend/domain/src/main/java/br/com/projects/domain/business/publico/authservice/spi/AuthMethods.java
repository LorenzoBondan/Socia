package br.com.projects.domain.business.publico.authservice.spi;

import br.com.projects.domain.business.publico.user.DUser;

public interface AuthMethods {

    DUser authenticated();
    void validateSelfOrAdmin(Long userId);
}
