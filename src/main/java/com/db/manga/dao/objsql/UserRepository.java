package com.db.manga.dao.objsql;

import com.db.manga.entity.objsql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        WITH inserted_user AS (
            INSERT INTO "user" (user_data)
            VALUES (ROW(:username, :password, :email, :signupDate)::user_type)
            RETURNING id
        )
        INSERT INTO user_role (user_id, role_id)
        SELECT (SELECT id FROM inserted_user), unnest(:roleIds);
        """, nativeQuery = true)
    void addUserWithRoles(@Param("username") String username,
                          @Param("password") String password,
                          @Param("email") String email,
                          @Param("signupDate") String signupDate,
                          @Param("roleIds") Long[] roleIds);



}
