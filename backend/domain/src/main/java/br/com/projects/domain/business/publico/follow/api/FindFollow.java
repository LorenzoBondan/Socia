package br.com.projects.domain.business.publico.follow.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.follow.DFollow;

public interface FindFollow {

    DFollow find (Integer id);
    Paged<DFollow> find (PageableRequest request);
}
