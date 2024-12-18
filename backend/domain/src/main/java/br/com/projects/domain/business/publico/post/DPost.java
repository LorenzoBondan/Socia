package br.com.projects.domain.business.publico.post;

import br.com.projects.domain.business.publico.comment.DComment;
import br.com.projects.domain.business.publico.postattachment.DPostAttachment;
import br.com.projects.domain.business.publico.postlike.DPostLike;
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
public class DPost {

    @EqualsAndHashCode.Include
    private Integer id;
    private DUser userAuthor;
    private String description;
    private LocalDateTime date;
    private Boolean isEdited;

    private List<DPostAttachment> attachments = new ArrayList<>();
    private List<DPostLike> likes = new ArrayList<>();
    private List<DComment> comments = new ArrayList<>();

    public DPost(Integer id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("User Author", new ObjetoNaoNuloValidator()), this.userAuthor)
                .add(new NamedValidator<>("Description", new ObjetoNaoNuloValidator()), this.description)
                .add(new NamedValidator<>("Description", new NaoBrancoValidator()), this.description)
                .add(new NamedValidator<>("Description", new TamanhoMaximoValidator(100)), this.description)
                .add(new NamedValidator<>("Date", new ObjetoNaoNuloValidator()), this.date)
                .add(new NamedValidator<>("Is Edited", new ObjetoNaoNuloValidator()), this.isEdited)
                .validate();
    }
}
