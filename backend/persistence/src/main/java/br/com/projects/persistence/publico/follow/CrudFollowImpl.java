package br.com.projects.persistence.publico.follow;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.PagedBuilder;
import br.com.projects.domain.business.exceptions.DatabaseException;
import br.com.projects.domain.business.exceptions.ResourceNotFoundException;
import br.com.projects.domain.business.publico.follow.DFollow;
import br.com.projects.domain.business.publico.follow.spi.CrudFollow;
import br.com.projects.persistence.entities.Follow;
import br.com.projects.persistence.util.PageRequestUtils;
import br.com.projects.persistence.util.SpecificationHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Component
public class CrudFollowImpl implements CrudFollow {

    private final FollowRepository repository;
    private final FollowQueryRepository queryRepository;
    private final FollowDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;

    public CrudFollowImpl(FollowRepository repository, FollowQueryRepository queryRepository, FollowDomainToEntityAdapter adapter, PageRequestUtils pageRequestUtils) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.pageRequestUtils = pageRequestUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DFollow> findAll(PageableRequest request) {
        SpecificationHelper<Follow> helper = new SpecificationHelper<>();
        Specification<Follow> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DFollow>()
                        .withContent(r.getContent().stream().map(adapter::toDomain).toList())
                        .withSortedBy(String.join(";", request.getSort()))
                        .withFirst(r.isFirst())
                        .withLast(r.isLast())
                        .withPage(r.getNumber())
                        .withSize(r.getSize())
                        .withTotalPages(r.getTotalPages())
                        .withNumberOfElements(Math.toIntExact(r.getTotalElements()))
                        .build())
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public DFollow find(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DFollow> findByUserFollowerAndUserFollowing(Integer userFollowerId, Integer userFollowingId) {
        return queryRepository.findByUserFollower_IdAndUserFollowing_Id(userFollowerId, userFollowingId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional
    public DFollow insert(DFollow obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    public DFollow update(DFollow obj) {
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Id not found: " + id);
        }
        try{
            repository.deleteById(id);
        } catch(DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
