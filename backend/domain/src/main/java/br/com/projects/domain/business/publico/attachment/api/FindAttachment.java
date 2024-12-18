package br.com.projects.domain.business.publico.attachment.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.attachment.DAttachment;

public interface FindAttachment {

    DAttachment find (Integer id);
    Paged<DAttachment> find (PageableRequest request);
}
