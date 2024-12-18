package br.com.projects.domain.business.publico.binary;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.binary.api.BinaryService;
import br.com.projects.domain.business.publico.binary.spi.CrudBinary;

@DomainService
public class BinaryServiceImpl implements BinaryService {

    private final CrudBinary crudBinary;

    public BinaryServiceImpl(CrudBinary crudBinary) {
        this.crudBinary = crudBinary;
    }

    @Override
    public Paged<DBinary> find(PageableRequest request) {
        return crudBinary.findAll(request);
    }

    @Override
    public DBinary find(Integer id) {
        return crudBinary.find(id);
    }

    @Override
    public DBinary insert(DBinary domain) {
        domain.validate();
        return crudBinary.insert(domain);
    }

    @Override
    public DBinary update(DBinary domain) {
        domain.validate();
        return crudBinary.update(domain);
    }

    @Override
    public void delete(Integer id) {
        crudBinary.delete(id);
    }
}
