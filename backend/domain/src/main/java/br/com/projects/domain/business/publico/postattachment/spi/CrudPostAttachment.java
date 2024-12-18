package br.com.projects.domain.business.publico.postattachment.spi;

import br.com.projects.domain.business.SimpleCrud;
import br.com.projects.domain.business.publico.postattachment.DPostAttachment;

import java.util.Collection;

public interface CrudPostAttachment extends SimpleCrud<DPostAttachment, Integer> {

    Collection<? extends DPostAttachment> findByPostAndAttachment (Integer postId, Integer attachmentId);
}
