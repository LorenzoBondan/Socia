package br.com.projects.domain.business.publico.commentlike.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.commentlike.DCommentLike;

public interface FindCommentLike {

    DCommentLike find (Integer id);
    Paged<DCommentLike> find (PageableRequest request);
}
