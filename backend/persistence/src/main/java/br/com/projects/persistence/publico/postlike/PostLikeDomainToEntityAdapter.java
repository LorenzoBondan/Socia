package br.com.projects.persistence.publico.postlike;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.post.DPost;
import br.com.projects.domain.business.publico.postlike.DPostLike;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.persistence.entities.Post;
import br.com.projects.persistence.entities.PostLike;
import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.publico.userattachment.UserAttachmentDomainToEntityAdapter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PostLikeDomainToEntityAdapter implements Convertable<PostLike, DPostLike> {

    private final UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter;

    public PostLikeDomainToEntityAdapter( UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter) {
        this.userAttachmentDomainToEntityAdapter = userAttachmentDomainToEntityAdapter;
    }

    @Override
    public PostLike toEntity(DPostLike domain) {
        return PostLike.builder()
                .id(domain.getId())
                .post(new Post(domain.getPost().getId()))
                .user(new User(domain.getUser().getId()))
                .build();
    }

    @Override
    public DPostLike toDomain(PostLike entity) {

        return DPostLike.builder()
                .id(entity.getId())
                .user(Optional.ofNullable(entity.getUser())
                        .map(user -> new DUser(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                Optional.ofNullable(user.getUserAttachment())
                                        .map(userAttachmentDomainToEntityAdapter::toDomain)
                                        .orElse(null)
                        ))
                        .orElse(null))
                .post(Optional.ofNullable(entity.getPost())
                        .map(Post -> new DPost(Post.getId()))
                        .orElse(null))
                .build();
    }
}
