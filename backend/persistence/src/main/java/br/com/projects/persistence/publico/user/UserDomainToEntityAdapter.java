package br.com.projects.persistence.publico.user;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.follow.DFollow;
import br.com.projects.domain.business.publico.post.DPost;
import br.com.projects.domain.business.publico.role.DRole;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.persistence.entities.Role;
import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.entities.UserAttachment;
import br.com.projects.persistence.publico.follow.FollowDomainToEntityAdapter;
import br.com.projects.persistence.publico.post.PostDomainToEntityAdapter;
import br.com.projects.persistence.publico.userattachment.UserAttachmentDomainToEntityAdapter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserDomainToEntityAdapter implements Convertable<User, DUser> {

    private final UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter;
    private final FollowDomainToEntityAdapter followDomainToEntityAdapter;
    private final PostDomainToEntityAdapter postDomainToEntityAdapter;

    public UserDomainToEntityAdapter(UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter, FollowDomainToEntityAdapter followDomainToEntityAdapter, PostDomainToEntityAdapter postDomainToEntityAdapter) {
        this.userAttachmentDomainToEntityAdapter = userAttachmentDomainToEntityAdapter;
        this.followDomainToEntityAdapter = followDomainToEntityAdapter;
        this.postDomainToEntityAdapter = postDomainToEntityAdapter;
    }

    @Override
    public User toEntity(DUser domain) {
        return User.builder()
                .id(domain.getId())
                .name(domain.getName())
                .password(domain.getPassword())
                .email(domain.getEmail())
                .userAttachment(Optional.ofNullable(domain.getUserAttachment())
                        .map(userAttachment -> new UserAttachment(userAttachment.getId()))
                        .orElse(null))

                .roles(domain.getRoles().stream().map(role -> new Role(role.getId(), role.getAuthority())).collect(Collectors.toSet()))

                .build();
    }

    @Override
    public DUser toDomain(User entity) {

        List<DFollow> following = Optional.ofNullable(entity.getFollowing())
                .map(lista -> lista.stream()
                        .map(followDomainToEntityAdapter::toDomain)
                        .toList())
                .orElse(List.of());

        List<DFollow> followers = Optional.ofNullable(entity.getFollowers())
                .map(lista -> lista.stream()
                        .map(followDomainToEntityAdapter::toDomain)
                        .toList())
                .orElse(List.of());

        List<DPost> posts = Optional.ofNullable(entity.getPosts())
                .map(lista -> lista.stream()
                        .map(postDomainToEntityAdapter::toDomain)
                        .toList())
                .orElse(List.of());

        return DUser.builder()
                .id(entity.getId())
                .name(entity.getName())
                .password(entity.getPassword())
                .email(entity.getEmail())
                .userAttachment(Optional.ofNullable(entity.getUserAttachment())
                        .map(userAttachmentDomainToEntityAdapter::toDomain)
                        .orElse(null))

                .roles(entity.getRoles().stream().map(role -> new DRole(role.getId(), role.getAuthority())).collect(Collectors.toList()))
                .following(following)
                .followers(followers)
                .posts(posts)

                .build();
    }
}
