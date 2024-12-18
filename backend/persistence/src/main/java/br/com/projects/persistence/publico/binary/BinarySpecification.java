package br.com.projects.persistence.publico.binary;

import br.com.projects.persistence.entities.Binary;
import br.com.projects.persistence.util.SearchCriteria;
import br.com.projects.persistence.util.SearchSpecificationImpl;

public class BinarySpecification extends SearchSpecificationImpl<Binary> {

    public BinarySpecification(SearchCriteria criteria) {
        super(criteria);
    }
}
