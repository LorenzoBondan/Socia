package br.com.projects.domain.business.publico.binary.api;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.publico.binary.DBinary;

public interface FindBinary {

    DBinary find (Integer id);
    Paged<DBinary> find (PageableRequest request);
}
