package com.db.manga.dao.sql;

import com.db.manga.entity.sql.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Profile({"sql", "objsql", "obj"})
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);


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


    @Modifying
    @Transactional
    @Query(value = """
        UPDATE "user"
        SET user_data = ROW(:username, :password, :email, :signupDate)::user_type
        WHERE id = :userId;
    """, nativeQuery = true)
    void updateUser(@Param("userId") long userId,
                    @Param("username") String username,
                    @Param("password") String password,
                    @Param("email") String email,
                    @Param("signupDate") String signupDate);

    @Modifying
    @Transactional
    @Query(value = """
        WITH deleted_user AS (
            DELETE FROM "user"
            WHERE id = :userId
            RETURNING id
        )
        DELETE FROM user_role
        WHERE user_id = (SELECT id FROM deleted_user);
    """, nativeQuery = true)
    void deleteUser(@Param("userId") long userId);
}
