package br.com.projects.persistence.publico.post;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.PagedBuilder;
import br.com.projects.domain.business.exceptions.DatabaseException;
import br.com.projects.domain.business.exceptions.ResourceNotFoundException;
import br.com.projects.domain.business.publico.post.DPost;
import br.com.projects.domain.business.publico.post.spi.CrudPost;
import br.com.projects.persistence.entities.Post;
import br.com.projects.persistence.util.PageRequestUtils;
import br.com.projects.persistence.util.SpecificationHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class CrudPostImpl implements CrudPost {

    private final PostRepository repository;
    private final PostQueryRepository queryRepository;
    private final PostDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;

    public CrudPostImpl(PostRepository repository, PostQueryRepository queryRepository, PostDomainToEntityAdapter adapter, PageRequestUtils pageRequestUtils) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.pageRequestUtils = pageRequestUtils;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DPost> findAll(PageableRequest request) {
        SpecificationHelper<Post> helper = new SpecificationHelper<>();
        Specification<Post> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DPost>()
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
    public DPost find(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DPost insert(DPost obj) {
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DPost update(DPost obj) {
        if(!repository.existsById(obj.getId())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getId());
        }
        Post entity = adapter.toEntity(obj);
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
