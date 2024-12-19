package com.db.manga.service;

import com.db.manga.entity.sql.Role;
import com.db.manga.entity.sql.User;

import java.util.List;

public interface ObjSqlService {

    User getUserById(long id);

    void createUser(String userName, String password, String email, String signUpDate, List<Role> roles);

    void createRole(String roleDescription);

    void updateUser(long id, String userName, String password, String email, String signUpDate);

    void deleteUser(long id);

    List<Role> findAllRoles();

}
