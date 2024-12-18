package br.com.projects.domain.business.publico.user.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.user.DUser;

public interface FindUser {

    DUser find (Integer id);
    DUser find (String email);
    Paged<DUser> find(PageableRequest request);
}
