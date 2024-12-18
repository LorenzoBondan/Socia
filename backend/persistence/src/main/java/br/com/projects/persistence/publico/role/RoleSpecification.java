package br.com.projects.persistence.publico.role;

import br.com.projects.persistence.entities.Role;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class RoleSpecification extends SearchSpecificationImpl<Role> {

    public RoleSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
