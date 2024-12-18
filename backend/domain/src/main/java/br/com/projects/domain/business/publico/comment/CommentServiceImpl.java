package br.com.projects.domain.business.publico.comment;

import br.com.projects.domain.business.PageableRequest;
import br.com.projects.domain.business.Paged;
import br.com.projects.domain.business.exceptions.ForbiddenException;
import br.com.projects.domain.business.metadata.DomainService;
import br.com.projects.domain.business.publico.authservice.api.AuthService;
import br.com.projects.domain.business.publico.comment.api.CommentService;
import br.com.projects.domain.business.publico.comment.spi.CrudComment;
import br.com.projects.domain.business.publico.user.DUser;

@DomainService
public class CommentServiceImpl implements CommentService {

    private final CrudComment crudComment;
    private final AuthService authService;

    public CommentServiceImpl(CrudComment crudComment, AuthService authService) {
        this.crudComment = crudComment;
        this.authService = authService;
    }

    @Override
    public Paged<DComment> find(PageableRequest request) {
        return crudComment.findAll(request);
    }

    @Override
    public DComment find(Integer id) {
        return crudComment.find(id);
    }

    @Override
    public DComment insert(DComment domain) {
        domain.validate();
        return crudComment.insert(domain);
    }

    @Override
    public DComment insertCommentChildrenInCommentParent(DComment commentChildren, Integer commentParentId) {
        commentChildren.validate();
        commentChildren = insert(commentChildren);

        DComment commentParent = find(commentParentId);
        commentParent.getComments().add(commentChildren);
        update(commentParent);
        
        return commentChildren;
    }

    @Override
    public DComment update(DComment domain) {
        DUser authenticated = authService.authenticated();
        if(!domain.getUser().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode alterar o próprio comentário.");
        }
        domain.validate();
        return crudComment.update(domain);
    }

    @Override
    public void delete(Integer id) {
        DComment domain = find(id);
        DUser authenticated = authService.authenticated();
        if(!domain.getUser().getId().equals(authenticated.getId()) || domain.getPost().getUserAuthor().getId().equals(authenticated.getId())) {
            throw new ForbiddenException("Só pode excluir o próprio comentário ou um comentário em sua publicação.");
        }
        crudComment.delete(id);
    }
}
