package br.com.projects.persistence.publico.binary;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.binary.DBinary;
import br.com.projects.persistence.entities.Binary;
import org.springframework.stereotype.Component;

@Component
public class BinaryDomainToEntityAdapter implements Convertable<Binary, DBinary> {

    @Override
    public Binary toEntity(DBinary domain) {
        return Binary.builder()
                .id(domain.getId())
                .bytes(domain.getBytes())
                .build();
    }

    @Override
    public DBinary toDomain(Binary entity) {
        return DBinary.builder()
                .id(entity.getId())
                .bytes(entity.getBytes())
                .build();
    }
}
