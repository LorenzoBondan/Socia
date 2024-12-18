package br.com.projects.persistence.publico.commentlike;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.PagedBuilder;
import br.com.projects.domain.business.exceptions.DatabaseException;
import br.com.projects.domain.business.exceptions.ResourceNotFoundException;
import br.com.projects.domain.business.publico.commentlike.DCommentLike;
import br.com.projects.domain.business.publico.commentlike.spi.CrudCommentLike;
import br.com.projects.persistence.entities.CommentLike;
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
public class CrudCommentLikeImpl implements CrudCommentLike {

    private final CommentLikeRepository repository;
    private final CommentLikeQueryRepository queryRepository;
    private final CommentLikeDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;

    public CrudCommentLikeImpl(CommentLikeRepository repository, CommentLikeQueryRepository queryRepository, CommentLikeDomainToEntityAdapter adapter, PageRequestUtils pageRequestUtils) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.pageRequestUtils = pageRequestUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DCommentLike> findAll(PageableRequest request) {
        SpecificationHelper<CommentLike> helper = new SpecificationHelper<>();
        Specification<CommentLike> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DCommentLike>()
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
    public DCommentLike find(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DCommentLike> findByCommentAndUser(Integer commentId, Integer userId) {
        return queryRepository.findByComment_IdAndUser_Id(commentId, userId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional
    public DCommentLike insert(DCommentLike obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DCommentLike update(DCommentLike obj) {
        if(!repository.existsById(obj.getId())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getId());
        }
        CommentLike entity = adapter.toEntity(obj);
        return adapter.toDomain(repository.save(entity));
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
