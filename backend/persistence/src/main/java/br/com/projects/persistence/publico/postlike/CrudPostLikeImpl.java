package br.com.projects.persistence.publico.postlike;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.PagedBuilder;
import br.com.projects.domain.business.exceptions.DatabaseException;
import br.com.projects.domain.business.exceptions.ResourceNotFoundException;
import br.com.projects.domain.business.publico.postlike.DPostLike;
import br.com.projects.domain.business.publico.postlike.spi.CrudPostLike;
import br.com.projects.persistence.entities.PostLike;
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
public class CrudPostLikeImpl implements CrudPostLike {

    private final PostLikeRepository repository;
    private final PostLikeQueryRepository queryRepository;
    private final PostLikeDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;

    public CrudPostLikeImpl(PostLikeRepository repository, PostLikeQueryRepository queryRepository, PostLikeDomainToEntityAdapter adapter, PageRequestUtils pageRequestUtils) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.pageRequestUtils = pageRequestUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DPostLike> findAll(PageableRequest request) {
        SpecificationHelper<PostLike> helper = new SpecificationHelper<>();
        Specification<PostLike> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DPostLike>()
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
    public DPostLike find(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DPostLike> findByPostAndUser(Integer PostId, Integer userId) {
        return queryRepository.findByPost_IdAndUser_Id(PostId, userId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional
    public DPostLike insert(DPostLike obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DPostLike update(DPostLike obj) {
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
