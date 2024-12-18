package br.com.projects.domain.business.projections;

public interface UserDetailsProjection {
    String getUsername();
    String getPassword();
    Integer getRoleId();
    String getAuthority();
}
