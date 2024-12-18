package br.com.projects.domain.business.publico.role;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.role.api.RoleService;
import br.com.projects.domain.business.publico.role.spi.CrudRole;

@DomainService
public class RoleServiceImpl implements RoleService {

    private final CrudRole crudRole;

    public RoleServiceImpl(CrudRole crudRole) {
        this.crudRole = crudRole;
    }

    @Override
    public Paged<DRole> find(PageableRequest request) {
        return crudRole.findAll(request);
    }

    @Override
    public DRole find(Integer id) {
        return crudRole.find(id);
    }
}
