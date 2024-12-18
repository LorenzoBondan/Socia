package br.com.projects.persistence.publico.role;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.role.DRole;
import br.com.projects.persistence.entities.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleDomainToEntityAdapter implements Convertable<Role, DRole> {

    @Override
    public Role toEntity(DRole domain) {
        return Role.builder()
                .id(domain.getId())
                .authority(domain.getAuthority())
                .build();
    }

    @Override
    public DRole toDomain(Role entity) {
        return DRole.builder()
                .id(entity.getId())
                .authority(entity.getAuthority())
                .build();
    }
}
