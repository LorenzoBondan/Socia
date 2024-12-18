package br.com.projects.domain.business.publico.post;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.exceptions.ForbiddenException;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.authservice.api.AuthService;
import br.com.projects.domain.business.publico.follow.DFollow;
import br.com.projects.domain.business.publico.post.api.PostService;
import br.com.projects.domain.business.publico.post.spi.CrudPost;
import br.com.projects.domain.business.publico.user.DUser;
import br.com.projects.domain.business.publico.user.api.UserService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@DomainService
public class PostServiceImpl implements PostService {

    private final CrudPost crudPost;
    private final UserService userService;
    private final AuthService authService;

    public PostServiceImpl(CrudPost crudPost, UserService userService, AuthService authService) {
        this.crudPost = crudPost;
        this.userService = userService;
        this.authService = authService;
    }

    @Override
    public Paged<DPost> find(PageableRequest request) {
        return crudPost.findAll(request);
    }

    @Override
    public Paged<DPost> findFeed(Integer userId, PageableRequest request) {
        DUser user = userService.find(userId);

        DUser authenticated = authService.authenticated();
        if(!user.getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode ver o feed do próprio Usuário.");
        }

        List<DPost> allPosts = new ArrayList<>();

        user.getFollowing().stream()
                .map(DFollow::getUserFollowing)
                .filter(Objects::nonNull)
                .flatMap(following -> following.getPosts().stream())
                .forEach(allPosts::add);

        allPosts.addAll(user.getPosts());
        allPosts.sort(Comparator.comparing(DPost::getDate).reversed());

        int totalElements = allPosts.size();
        int totalPages = (int) Math.ceil((double) totalElements / request.getPageSize());
        int start = Math.min(request.getPage() * request.getPageSize(), totalElements);
        int end = Math.min(start + request.getPageSize(), totalElements);
        List<DPost> pagedContent = allPosts.subList(start, end);

        return new Paged<>(pagedContent.size(), request.getPage(), totalPages, request.getPageSize(), request.getPage() == 0, request.getPage() + 1 == totalPages, "date;d", pagedContent);
    }

    @Override
    public DPost find(Integer id) {
        return crudPost.find(id);
    }

    @Override
    public DPost insert(DPost domain) {
        DUser authenticated = authService.authenticated();
        if(!domain.getUserAuthor().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode postar com o próprio Usuário.");
        }
        domain.validate();
        return crudPost.insert(domain);
    }

    @Override
    public DPost update(DPost domain) {
        DUser authenticated = authService.authenticated();
        if(!domain.getUserAuthor().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode alterar uma publicação do próprio Usuário.");
        }
        domain.validate();
        return crudPost.update(domain);
    }

    @Override
    public void delete(Integer id) {
        DPost domain = find(id);
        DUser authenticated = authService.authenticated();
        if(!domain.getUserAuthor().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode excluir uma própria publicação.");
        }
        crudPost.delete(id);
    }
}
