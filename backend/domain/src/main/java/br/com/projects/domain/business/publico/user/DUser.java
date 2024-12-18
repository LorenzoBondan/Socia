package br.com.projects.domain.business.publico.user;

import br.com.projects.domain.business.publico.follow.DFollow;
import br.com.projects.domain.business.publico.post.DPost;
import br.com.projects.domain.business.publico.role.DRole;
import br.com.projects.domain.business.publico.userattachment.DUserAttachment;
import br.com.projects.domain.business.validation.NamedValidator;
import br.com.projects.domain.business.validation.ValidationBuilder;
import br.com.projects.domain.business.validation.impl.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DUser {

    @EqualsAndHashCode.Include
    private Integer id;
    private String name;
    private String password;
    private String email;
    private DUserAttachment userAttachment;

    private List<DRole> roles = new ArrayList<>();
    private List<DFollow> following = new ArrayList<>();
    private List<DFollow> followers = new ArrayList<>();
    private List<DPost> posts = new ArrayList<>();

    public DUser(Integer id){
        this.id = id;
    }

    public DUser(Integer id, String name, String email, DUserAttachment userAttachment) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userAttachment = userAttachment;
    }

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("Name", new ObjetoNaoNuloValidator()), this.name)
                .add(new NamedValidator<>("Name", new NaoBrancoValidator()), this.name)
                .add(new NamedValidator<>("Name", new CaracteresEspeciaisValidator()), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMinimoValidator(3)), this.name)
                .add(new NamedValidator<>("Name", new TamanhoMaximoValidator(50)), this.name)
                .add(new NamedValidator<>("Password", new ObjetoNaoNuloValidator()), this.password)
                .add(new NamedValidator<>("Password", new NaoBrancoValidator()), this.password)
                .add(new NamedValidator<>("Password", new TamanhoMinimoValidator(5)), this.password)
                .add(new NamedValidator<>("Password", new TamanhoMaximoValidator(50)), this.password)
                .add(new NamedValidator<>("Email", new ObjetoNaoNuloValidator()), this.email)
                .add(new NamedValidator<>("Email", new EmailValidator()), this.email)
                .add(new NamedValidator<>("Roles", new ObjetoNaoNuloValidator()), this.roles)
                .validate();
    }
}
