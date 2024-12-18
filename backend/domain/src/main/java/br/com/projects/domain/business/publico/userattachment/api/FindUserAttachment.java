package br.com.projects.domain.business.publico.userattachment.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.userattachment.DUserAttachment;

public interface FindUserAttachment {

    DUserAttachment find (Integer id);
    Paged<DUserAttachment> find (PageableRequest request);
}
