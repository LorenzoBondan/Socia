package br.com.projects.domain.business;

/**
 * Métodos padrão para CRUDs.
 * @param <T> é a entidade
 * @param <J> é a PK da entidade
 */
public interface SimpleCrud<T, J>{

    Paged<T> findAll(PageableRequest request);

    T find(J obj);

    T insert(T obj);

    T update(T obj);

    void delete(J obj);
}
