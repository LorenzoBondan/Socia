package br.com.projects.domain.business;

public interface Convertable<E, D>{
    E toEntity(D domain);
    D toDomain(E entity);
}
