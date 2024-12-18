package br.com.projects.persistence.publico.postlike;

import br.com.projects.persistence.entities.PostLike;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class PostLikeSpecification extends SearchSpecificationImpl<PostLike> {

    public PostLikeSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
