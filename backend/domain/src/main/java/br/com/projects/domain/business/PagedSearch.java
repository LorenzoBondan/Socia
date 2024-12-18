package br.com.projects.domain.business;

public interface PagedSearch<T>{

    <T> T search(PageableRequest request);
}
