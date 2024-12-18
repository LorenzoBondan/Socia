package br.com.projects.persistence.publico.attachment;

import br.com.projects.persistence.entities.Attachment;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class AttachmentSpecification extends SearchSpecificationImpl<Attachment> {

    public AttachmentSpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
