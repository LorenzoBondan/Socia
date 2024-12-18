package br.com.projects.domain.business.publico.role;

import br.com.projects.domain.business.validation.NamedValidator;
import br.com.projects.domain.business.validation.ValidationBuilder;
import br.com.projects.domain.business.validation.impl.NaoBrancoValidator;
import br.com.projects.domain.business.validation.impl.ObjetoNaoNuloValidator;
import br.com.projects.domain.business.validation.impl.TamanhoMaximoValidator;
import br.com.projects.domain.business.validation.impl.TamanhoMinimoValidator;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DRole {

    @EqualsAndHashCode.Include
    private Integer id;
    private String authority;

    public DRole(Integer id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Papel", new ObjetoNaoNuloValidator()), this.authority)
                .add(new NamedValidator<>("Papel", new NaoBrancoValidator()), this.authority)
                .add(new NamedValidator<>("Papel", new TamanhoMinimoValidator(8)), this.authority)
                .add(new NamedValidator<>("Papel", new TamanhoMaximoValidator(30)), this.authority)
                .validate();
    }
}
