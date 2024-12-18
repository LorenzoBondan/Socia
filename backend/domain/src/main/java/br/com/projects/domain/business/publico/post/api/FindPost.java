package br.com.projects.domain.business.publico.post.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.post.DPost;

public interface FindPost {

    DPost find (Integer id);
    Paged<DPost> find (PageableRequest request);
    Paged<DPost> findFeed (Integer userId, PageableRequest request);
}
