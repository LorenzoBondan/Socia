package br.com.projects.domain.business.publico.postlike;

import br.com.projects.domain.business.publico.post.DPost;
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
public class DPostLike {

    @EqualsAndHashCode.Include
    private Integer id;
    private DPost post;
    private DUser user;

    public DPostLike(Integer id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Post", new ObjetoNaoNuloValidator()), this.post)
                .add(new NamedValidator<>("User", new ObjetoNaoNuloValidator()), this.user)
                .validate();
    }
}
