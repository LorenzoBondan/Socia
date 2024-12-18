package br.com.projects.domain.business.publico.binary;

import br.com.projects.domain.business.Descritivel;
import br.com.projects.domain.business.exceptions.ValidationException;
import br.com.projects.domain.business.validation.NamedValidator;
import br.com.projects.domain.business.validation.ValidationBuilder;
import br.com.projects.domain.business.validation.impl.ObjetoNaoNuloValidator;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DBinary implements Descritivel {

    @EqualsAndHashCode.Include
    private Integer id;
    private byte[] bytes;

    public DBinary(Integer id){
        this.id = id;
    }

    public void validate() throws ValidationException {
        new ValidationBuilder()
                .add(new NamedValidator<>("Bytes", new ObjetoNaoNuloValidator()), this.bytes)
                .validate();
    }

    @Override
    public String getDescricao() {
        return null;
    }
}
