package br.com.projects.domain.business.publico.role.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.role.DRole;

public interface FindRole {

    DRole find (Integer id);
    Paged<DRole> find(PageableRequest request);
}
