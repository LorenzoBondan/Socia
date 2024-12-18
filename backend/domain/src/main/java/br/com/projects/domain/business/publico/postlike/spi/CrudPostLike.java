package br.com.projects.domain.business.publico.postlike.spi;

import br.com.projects.domain.business.SimpleCrud;
import br.com.projects.domain.business.publico.postlike.DPostLike;

import java.util.Collection;

public interface CrudPostLike extends SimpleCrud<DPostLike, Integer> {

    Collection<? extends DPostLike> findByPostAndUser (Integer postId, Integer userId);
}
