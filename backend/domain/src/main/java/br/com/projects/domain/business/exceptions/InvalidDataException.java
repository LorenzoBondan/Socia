package br.com.projects.domain.business.exceptions;

import java.io.Serial;

public class InvalidDataException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;
    public InvalidDataException(String msg){
        super(msg);
    }
}