package br.com.projects.domain.business.publico.comment.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.comment.DComment;

public interface FindComment {

    DComment find (Integer id);
    Paged<DComment> find (PageableRequest request);
}
