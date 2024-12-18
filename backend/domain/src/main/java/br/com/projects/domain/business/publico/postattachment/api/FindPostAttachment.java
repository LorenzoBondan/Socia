package br.com.projects.domain.business.publico.postattachment.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.postattachment.DPostAttachment;

public interface FindPostAttachment {

    DPostAttachment find (Integer id);
    Paged<DPostAttachment> find (PageableRequest request);
}
