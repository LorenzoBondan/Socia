package br.com.projects.domain.business.publico.postlike.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.postlike.DPostLike;

public interface FindPostLike {

    DPostLike find (Integer id);
    Paged<DPostLike> find (PageableRequest request);
}
