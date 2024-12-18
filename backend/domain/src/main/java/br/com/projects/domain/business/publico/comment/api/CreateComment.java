package br.com.projects.domain.business.publico.comment.api;

import br.com.projects.domain.business.publico.comment.DComment;

public interface CreateComment {

    DComment insert (DComment comment);
    DComment insertCommentChildrenInCommentParent (DComment commentChildren, Integer commentParentId);
}
