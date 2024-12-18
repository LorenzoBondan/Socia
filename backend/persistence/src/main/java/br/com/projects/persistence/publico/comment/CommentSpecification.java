package br.com.projects.persistence.publico.comment;

import br.com.projects.persistence.entities.Comment;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class CommentSpecification extends SearchSpecificationImpl<Comment> {

    public CommentSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
