package br.com.projects.persistence.publico.post;

import br.com.projects.persistence.entities.Post;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class PostSpecification extends SearchSpecificationImpl<Post> {

    public PostSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
