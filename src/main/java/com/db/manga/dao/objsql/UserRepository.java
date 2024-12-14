package com.db.manga.dao.objsql;

import com.db.manga.entity.objsql.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
