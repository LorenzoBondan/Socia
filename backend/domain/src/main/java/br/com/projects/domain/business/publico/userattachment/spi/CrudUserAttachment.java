package br.com.projects.domain.business.publico.userattachment.spi;

import br.com.projects.domain.business.SimpleCrud;
import br.com.projects.domain.business.publico.userattachment.DUserAttachment;

import java.util.Collection;

public interface CrudUserAttachment extends SimpleCrud<DUserAttachment, Integer> {

    Collection<? extends DUserAttachment> findByUserAndAttachment (Integer userId, Integer attachmentId);
}
