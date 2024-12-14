package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
