package br.com.projects.domain.business.publico.follow;

import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.domain.business.validation.NamedValidator;
import br.com.projects.domain.business.validation.ValidationBuilder;
import br.com.projects.domain.business.validation.impl.ObjetoNaoNuloValidator;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DFollow {

    @EqualsAndHashCode.Include
    private Integer id;
    private DUser userFollower;
    private DUser userFollowing;
    private LocalDateTime date;

    public DFollow(Integer id){
        this.id = id;
    };

    public void validate(){
        new ValidationBuilder()
                .add(new NamedValidator<>("User Follower", new ObjetoNaoNuloValidator()), this.userFollower)
                .add(new NamedValidator<>("User Following", new ObjetoNaoNuloValidator()), this.userFollowing)
                .add(new NamedValidator<>("Date", new ObjetoNaoNuloValidator()), this.date)
                .validate();
    }
}
