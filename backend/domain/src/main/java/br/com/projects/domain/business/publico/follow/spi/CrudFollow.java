package br.com.projects.domain.business.publico.follow.spi;

import br.com.projects.domain.business.SimpleCrud;
import br.com.projects.domain.business.publico.follow.DFollow;

import java.util.Collection;

public interface CrudFollow extends SimpleCrud<DFollow, Integer> {

    Collection<? extends DFollow> findByUserFollowerAndUserFollowing (Integer userFollowerId, Integer userFollowingId);
}
