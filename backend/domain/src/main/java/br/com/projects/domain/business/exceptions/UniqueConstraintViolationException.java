package br.com.projects.domain.business.exceptions;

import lombok.Getter;

@Getter
public class UniqueConstraintViolationException extends RuntimeException {

    public UniqueConstraintViolationException(String msg){
        super(msg);
    }
}
