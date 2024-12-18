package br.com.projects.persistence.publico.userattachment;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.PagedBuilder;
import br.com.projects.domain.business.exceptions.DatabaseException;
import br.com.projects.domain.business.exceptions.ResourceNotFoundException;
import br.com.projects.domain.business.publico.userattachment.DUserAttachment;
import br.com.projects.domain.business.publico.userattachment.spi.CrudUserAttachment;
import br.com.projects.persistence.entities.UserAttachment;
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
public class CrudUserAttachmentImpl implements CrudUserAttachment {

    private final UserAttachmentRepository repository;
    private final UserAttachmentQueryRepository queryRepository;
    private final UserAttachmentDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;

    public CrudUserAttachmentImpl(UserAttachmentRepository repository, UserAttachmentQueryRepository queryRepository, UserAttachmentDomainToEntityAdapter adapter, PageRequestUtils pageRequestUtils) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.pageRequestUtils = pageRequestUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DUserAttachment> findAll(PageableRequest request) {
        SpecificationHelper<UserAttachment> helper = new SpecificationHelper<>();
        Specification<UserAttachment> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DUserAttachment>()
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
    public DUserAttachment find(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<? extends DUserAttachment> findByUserAndAttachment(Integer UserId, Integer attachmentId) {
        return queryRepository.findByUser_IdAndAttachment_Id(UserId, attachmentId).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional
    public DUserAttachment insert(DUserAttachment obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DUserAttachment update(DUserAttachment obj) {
        if(!repository.existsById(obj.getId())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getId());
        }
        UserAttachment entity = adapter.toEntity(obj);
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
