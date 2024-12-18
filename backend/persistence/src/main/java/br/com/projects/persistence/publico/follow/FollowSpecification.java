package br.com.projects.persistence.publico.follow;

import br.com.projects.persistence.entities.Follow;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class FollowSpecification extends SearchSpecificationImpl<Follow> {

    public FollowSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
