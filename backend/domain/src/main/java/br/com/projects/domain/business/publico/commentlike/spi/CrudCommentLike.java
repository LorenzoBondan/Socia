package br.com.projects.domain.business.publico.commentlike.spi;

import br.com.projects.domain.business.SimpleCrud;
import br.com.projects.domain.business.publico.commentlike.DCommentLike;

import java.util.Collection;

public interface CrudCommentLike extends SimpleCrud<DCommentLike, Integer> {

    Collection<? extends DCommentLike> findByCommentAndUser (Integer commentId, Integer userId);
}
