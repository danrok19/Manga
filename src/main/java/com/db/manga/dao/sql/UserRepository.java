package com.db.manga.dao.sql;

import com.db.manga.entity.sql.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
