package br.com.projects.persistence.publico.userattachment;

import br.com.projects.persistence.entities.UserAttachment;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class UserAttachmentSpecification extends SearchSpecificationImpl<UserAttachment> {

    public UserAttachmentSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
