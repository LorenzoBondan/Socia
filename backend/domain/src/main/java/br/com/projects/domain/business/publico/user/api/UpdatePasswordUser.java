package br.com.projects.domain.business.publico.user.api;

public interface UpdatePasswordUser {

    void updatePassword (String newPassword, String oldPassword);
}
