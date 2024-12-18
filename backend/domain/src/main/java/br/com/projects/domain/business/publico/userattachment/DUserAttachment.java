package br.com.projects.domain.business.publico.userattachment;

import br.com.projects.domain.business.publico.attachment.DAttachment;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.domain.business.validation.NamedValidator;
import br.com.projects.domain.business.validation.ValidationBuilder;
import br.com.projects.domain.business.validation.impl.ObjetoNaoNuloValidator;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DUserAttachment {

    @EqualsAndHashCode.Include
    private Integer id;
    private DUser user;
    private DAttachment attachment;

    public DUserAttachment(Integer id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("User", new ObjetoNaoNuloValidator()), this.user)
                .add(new NamedValidator<>("Attachment", new ObjetoNaoNuloValidator()), this.attachment)
                .validate();
    }
}
