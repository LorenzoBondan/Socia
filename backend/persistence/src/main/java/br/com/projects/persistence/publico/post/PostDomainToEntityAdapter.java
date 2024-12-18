package br.com.projects.persistence.publico.post;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.comment.DComment;
import br.com.projects.domain.business.publico.post.DPost;
import br.com.projects.domain.business.publico.postattachment.DPostAttachment;
import br.com.projects.domain.business.publico.postlike.DPostLike;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.persistence.entities.Post;
import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.publico.comment.CommentDomainToEntityAdapter;
import br.com.projects.persistence.publico.postattachment.PostAttachmentDomainToEntityAdapter;
import br.com.projects.persistence.publico.postlike.PostLikeDomainToEntityAdapter;
import br.com.projects.persistence.publico.userattachment.UserAttachmentDomainToEntityAdapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PostDomainToEntityAdapter implements Convertable<Post, DPost> {

    private final CommentDomainToEntityAdapter commentDomainToEntityAdapter;
    private final PostAttachmentDomainToEntityAdapter postAttachmentDomainToEntityAdapter;
    private final PostLikeDomainToEntityAdapter postLikeDomainToEntityAdapter;
    private final UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter;

    public PostDomainToEntityAdapter(CommentDomainToEntityAdapter commentDomainToEntityAdapter, PostAttachmentDomainToEntityAdapter postAttachmentDomainToEntityAdapter, PostLikeDomainToEntityAdapter postLikeDomainToEntityAdapter, UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter) {
        this.commentDomainToEntityAdapter = commentDomainToEntityAdapter;
        this.postAttachmentDomainToEntityAdapter = postAttachmentDomainToEntityAdapter;
        this.postLikeDomainToEntityAdapter = postLikeDomainToEntityAdapter;
        this.userAttachmentDomainToEntityAdapter = userAttachmentDomainToEntityAdapter;
    }

    @Override
    public Post toEntity(DPost domain) {
        return Post.builder()
                .id(domain.getId())
                .userAuthor(new User(domain.getUserAuthor().getId()))
                .description(domain.getDescription())
                .date(domain.getDate())
                .isEdited(domain.getIsEdited())
                .build();
    }

    @Override
    public DPost toDomain(Post entity) {

        List<DPostAttachment> attachments = Optional.ofNullable(entity.getPostAttachments())
                .map(lista -> lista.stream()
                        .map(postAttachmentDomainToEntityAdapter::toDomain)
                        .toList())
                .orElse(List.of());

        List<DComment> comments = Optional.ofNullable(entity.getComments())
                .map(lista -> lista.stream()
                        .map(commentDomainToEntityAdapter::toDomain)
                        .toList())
                .orElse(List.of());

        List<DPostLike> likes = Optional.ofNullable(entity.getLikes())
                .map(lista -> lista.stream()
                        .map(postLikeDomainToEntityAdapter::toDomain)
                        .toList())
                .orElse(List.of());

        return DPost.builder()
                .id(entity.getId())
                .userAuthor(Optional.ofNullable(entity.getUserAuthor())
                        .map(user -> new DUser(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                Optional.ofNullable(user.getUserAttachment())
                                        .map(userAttachmentDomainToEntityAdapter::toDomain)
                                        .orElse(null)
                        ))
                        .orElse(null))
                .description(entity.getDescription())
                .date(entity.getDate())
                .isEdited(entity.getIsEdited())

                .attachments(attachments)
                .comments(comments)
                .likes(likes)

                .build();
    }
}
