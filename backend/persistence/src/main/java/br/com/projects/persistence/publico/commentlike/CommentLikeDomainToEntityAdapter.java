package br.com.projects.persistence.publico.commentlike;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.comment.DComment;
import br.com.projects.domain.business.publico.commentlike.DCommentLike;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.persistence.entities.Comment;
import br.com.projects.persistence.entities.CommentLike;
import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.publico.userattachment.UserAttachmentDomainToEntityAdapter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommentLikeDomainToEntityAdapter implements Convertable<CommentLike, DCommentLike> {

    private final UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter;

    public CommentLikeDomainToEntityAdapter(UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter) {
        this.userAttachmentDomainToEntityAdapter = userAttachmentDomainToEntityAdapter;
    }

    @Override
    public CommentLike toEntity(DCommentLike domain) {
        return CommentLike.builder()
                .id(domain.getId())
                .comment(new Comment(domain.getComment().getId()))
                .user(new User(domain.getUser().getId()))
                .build();
    }

    @Override
    public DCommentLike toDomain(CommentLike entity) {

        return DCommentLike.builder()
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
                .comment(Optional.ofNullable(entity.getComment())
                        .map(comment -> new DComment(comment.getId()))
                        .orElse(null))
                .build();
    }
}
