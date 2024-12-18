package br.com.projects.persistence.publico.user;

import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class UserSpecification extends SearchSpecificationImpl<User> {

    public UserSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
