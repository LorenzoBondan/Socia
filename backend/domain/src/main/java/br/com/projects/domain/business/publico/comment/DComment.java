package br.com.projects.domain.business.publico.comment;

import br.com.projects.domain.business.publico.commentlike.DCommentLike;
import br.com.projects.domain.business.publico.post.DPost;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.domain.business.validation.NamedValidator;
import br.com.projects.domain.business.validation.ValidationBuilder;
import br.com.projects.domain.business.validation.impl.NaoBrancoValidator;
import br.com.projects.domain.business.validation.impl.ObjetoNaoNuloValidator;
import br.com.projects.domain.business.validation.impl.TamanhoMaximoValidator;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DComment {

    @EqualsAndHashCode.Include
    private Integer id;
    private DUser user;
    private DPost post;
    private String description;
    private LocalDateTime date;
    private Boolean isEdited;

    private List<DComment> comments = new ArrayList<>();
    private List<DCommentLike> likes = new ArrayList<>();

    public DComment(Integer id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("User", new ObjetoNaoNuloValidator()), this.user)
                .add(new NamedValidator<>("Post", new ObjetoNaoNuloValidator()), this.post)
                .add(new NamedValidator<>("Description", new ObjetoNaoNuloValidator()), this.description)
                .add(new NamedValidator<>("Description", new NaoBrancoValidator()), this.description)
                .add(new NamedValidator<>("Description", new TamanhoMaximoValidator(100)), this.description)
                .add(new NamedValidator<>("Date", new ObjetoNaoNuloValidator()), this.date)
                .add(new NamedValidator<>("Is Edited", new ObjetoNaoNuloValidator()), this.isEdited)
                .validate();
    }
}
