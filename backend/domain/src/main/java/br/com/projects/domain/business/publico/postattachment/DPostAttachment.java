package br.com.projects.domain.business.publico.postattachment;

import br.com.projects.domain.business.publico.attachment.DAttachment;
import br.com.projects.domain.business.publico.post.DPost;
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
public class DPostAttachment {

    @EqualsAndHashCode.Include
    private Integer id;
    private DPost post;
    private DAttachment attachment;

    public DPostAttachment(Integer id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Post", new ObjetoNaoNuloValidator()), this.post)
                .add(new NamedValidator<>("Attachment", new ObjetoNaoNuloValidator()), this.attachment)
                .validate();
    }
}
