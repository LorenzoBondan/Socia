package br.com.projects.domain.business.publico.commentlike;

import br.com.projects.domain.business.publico.comment.DComment;
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
public class DCommentLike {

    @EqualsAndHashCode.Include
    private Integer id;
    private DComment comment;
    private DUser user;

    public DCommentLike(Integer id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Comment", new ObjetoNaoNuloValidator()), this.comment)
                .add(new NamedValidator<>("User", new ObjetoNaoNuloValidator()), this.user)
                .validate();
    }
}
