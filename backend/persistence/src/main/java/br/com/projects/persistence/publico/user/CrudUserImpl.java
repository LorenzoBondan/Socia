package br.com.projects.persistence.publico.user;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.PagedBuilder;
import br.com.projects.domain.business.exceptions.DatabaseException;
import br.com.projects.domain.business.exceptions.ResourceNotFoundException;
import br.com.projects.domain.business.exceptions.ValidationException;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.domain.business.publico.user.spi.CrudUser;
import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.util.PageRequestUtils;
import br.com.projects.persistence.util.SpecificationHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Component
public class CrudUserImpl implements CrudUser {

    private final UserRepository repository;
    private final UserQueryRepository queryRepository;
    private final UserDomainToEntityAdapter adapter;
    private final PageRequestUtils pageRequestUtils;
    private final PasswordEncoder passwordEncoder;

    public CrudUserImpl(UserRepository repository, UserQueryRepository queryRepository, UserDomainToEntityAdapter adapter, PageRequestUtils pageRequestUtils, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.queryRepository = queryRepository;
        this.adapter = adapter;
        this.pageRequestUtils = pageRequestUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Paged<DUser> findAll(PageableRequest request) {
        SpecificationHelper<User> helper = new SpecificationHelper<>();
        Specification<User> specification = helper.buildSpecification(request.getColunas(), request.getOperacoes(), request.getValores());

        return Optional.of(queryRepository.findAll(specification, pageRequestUtils.toPage(request)))
                .map(r -> new PagedBuilder<DUser>()
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
    public Collection<? extends DUser> findByEmail(String email) {
        return repository.findByEmail(email).stream().map(adapter::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DUser find(Integer id) {
        return adapter.toDomain(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Código não encontrado: " + id)));
    }

    @Override
    @Transactional
    public DUser insert(DUser obj) {
        obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        return adapter.toDomain(repository.save(adapter.toEntity(obj)));
    }

    @Override
    @Transactional
    public DUser update(DUser obj) {
        if(!repository.existsById(obj.getId())){
            throw new ResourceNotFoundException("Código não encontrado: " + obj.getId());
        }
        if (!obj.getPassword().startsWith("$2a$")) {  // BCrypt hashes start with $2a$
            obj.setPassword(passwordEncoder.encode(obj.getPassword()));
        }
        User entity = adapter.toEntity(obj);
        return adapter.toDomain(repository.save(entity));
    }

    @Override
    @Transactional
    public void updatePassword(String newPassword, String oldPassword, DUser user) {
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ValidationException("Incorrect old password");
        }
        repository.updatePassword(newPassword, user.getId());
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
