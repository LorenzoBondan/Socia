package br.com.projects.persistence.publico.commentlike;

import br.com.projects.persistence.entities.CommentLike;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class CommentLikeSpecification extends SearchSpecificationImpl<CommentLike> {

    public CommentLikeSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
