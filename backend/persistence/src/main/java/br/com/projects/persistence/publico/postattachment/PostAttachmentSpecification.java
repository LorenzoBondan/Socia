package br.com.projects.persistence.publico.postattachment;

import br.com.projects.persistence.entities.PostAttachment;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class PostAttachmentSpecification extends SearchSpecificationImpl<PostAttachment> {

    public PostAttachmentSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
