package br.com.projects.persistence.util;

import org.springframework.data.jpa.domain.Specification;

/**
 * Método buscarTodos
 * Inteface genérica extende a inteface padrão do Spring
 */
public interface SearchSpecification<T> extends Specification<T> {
}
