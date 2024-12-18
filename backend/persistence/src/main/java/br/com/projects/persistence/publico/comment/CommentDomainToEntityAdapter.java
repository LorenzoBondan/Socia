package br.com.projects.persistence.publico.comment;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.comment.DComment;
import br.com.projects.domain.business.publico.commentlike.DCommentLike;
import br.com.projects.domain.business.publico.post.DPost;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.persistence.entities.Comment;
import br.com.projects.persistence.entities.Post;
import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.publico.commentlike.CommentLikeDomainToEntityAdapter;
import br.com.projects.persistence.publico.userattachment.UserAttachmentDomainToEntityAdapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CommentDomainToEntityAdapter implements Convertable<Comment, DComment> {

    private final CommentLikeDomainToEntityAdapter commentLikeDomainToEntityAdapter;
    private final UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter;

    public CommentDomainToEntityAdapter(CommentLikeDomainToEntityAdapter commentLikeDomainToEntityAdapter, UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter) {
        this.commentLikeDomainToEntityAdapter = commentLikeDomainToEntityAdapter;
        this.userAttachmentDomainToEntityAdapter = userAttachmentDomainToEntityAdapter;
    }

    @Override
    public Comment toEntity(DComment domain) {
        return Comment.builder()
                .id(domain.getId())
                .user(new User(domain.getUser().getId()))
                .post(new Post(domain.getPost().getId()))
                .description(domain.getDescription())
                .date(domain.getDate())
                .isEdited(domain.getIsEdited())
                .build();
    }

    @Override
    public DComment toDomain(Comment entity) {

        List<DComment> comments = Optional.ofNullable(entity.getComments())
                .map(lista -> lista.stream()
                        .map(this::toDomain)
                        .toList())
                .orElse(List.of());

        List<DCommentLike> likes = Optional.ofNullable(entity.getLikes())
                .map(lista -> lista.stream()
                        .map(commentLikeDomainToEntityAdapter::toDomain)
                        .toList())
                .orElse(List.of());

        return DComment.builder()
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
                        .map(post -> new DPost(post.getId()))
                        .orElse(null))
                .description(entity.getDescription())
                .date(entity.getDate())
                .isEdited(entity.getIsEdited())

                .comments(comments)
                .likes(likes)

                .build();
    }
}
