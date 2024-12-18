package br.com.projects.persistence.publico.postattachment;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.PagedBuilder;
import br.com.projects.domain.business.exceptions.DatabaseException;
import br.com.projects.domain.business.exceptions.ResourceNotFoundException;
import br.com.projects.domain.business.publico.postattachment.DPostAttachment;
import br.com.projects.domain.business.publico.postattachment.spi.CrudPostAttachment;
import br.com.projects.persistence.entities.PostAttachment;
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
public class CrudPostAttachmentImpl implements CrudPostAttachment {

    private final PostAttachmentRepository repository;
    private final PostAttachmentQueryRepository queryRepository;
    private final PostAttachmentDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;

    public CrudPostAttachmentImpl(PostAttachmentRepository repository, PostAttachmentQueryRepository queryRepository, PostAttachmentDomainToEntityAdapter adapter, PageRequestUtils pageRequestUtils) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.pageRequestUtils = pageRequestUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DPostAttachment> findAll(PageableRequest request) {
        SpecificationHelper<PostAttachment> helper = new SpecificationHelper<>();
        Specification<PostAttachment> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DPostAttachment>()
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
    public DPostAttachment find(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DPostAttachment> findByPostAndAttachment(Integer postId, Integer attachmentId) {
        return queryRepository.findByPost_IdAndAttachment_Id(postId, attachmentId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional
    public DPostAttachment insert(DPostAttachment obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DPostAttachment update(DPostAttachment obj) {
        if(!repository.existsById(obj.getId())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getId());
        }
        PostAttachment entity = adapter.toEntity(obj);
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
