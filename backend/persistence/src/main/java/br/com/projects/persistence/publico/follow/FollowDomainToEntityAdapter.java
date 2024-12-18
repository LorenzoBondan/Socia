package br.com.projects.persistence.publico.follow;

import br.com.projects.domain.business.Convertable;
import br.com.projects.domain.business.publico.follow.DFollow;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.domain.business.publico.userattachment.DUserAttachment;
import br.com.projects.persistence.entities.Follow;
import br.com.projects.persistence.entities.User;
import br.com.projects.persistence.entities.UserAttachment;
import br.com.projects.persistence.publico.userattachment.UserAttachmentDomainToEntityAdapter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FollowDomainToEntityAdapter implements Convertable<Follow, DFollow> {

    private final UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter;

    public FollowDomainToEntityAdapter(UserAttachmentDomainToEntityAdapter userAttachmentDomainToEntityAdapter) {
        this.userAttachmentDomainToEntityAdapter = userAttachmentDomainToEntityAdapter;
    }

    @Override
    public Follow toEntity(DFollow domain) {
        return Follow.builder()
                .id(domain.getId())
                .userFollower(new User(domain.getUserFollower().getId()))
                .userFollowing(new User(domain.getUserFollowing().getId()))
                .date(domain.getDate())
                .build();
    }

    @Override
    public DFollow toDomain(Follow entity) {

        return DFollow.builder()
                .id(entity.getId())
                .userFollower(Optional.ofNullable(entity.getUserFollower())
                        .map(user -> new DUser(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                Optional.ofNullable(user.getUserAttachment())
                                        .map(userAttachmentDomainToEntityAdapter::toDomain)
                                        .orElse(null)
                        ))
                        .orElse(null))
                .userFollowing(Optional.ofNullable(entity.getUserFollowing())
                        .map(user -> new DUser(
                                user.getId(),
                                user.getName(),
                                user.getEmail(),
                                Optional.ofNullable(user.getUserAttachment())
                                        .map(userAttachmentDomainToEntityAdapter::toDomain)
                                        .orElse(null)
                        ))
                        .orElse(null))
                .date(entity.getDate())
                .build();
    }
}
