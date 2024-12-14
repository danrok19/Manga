package com.db.manga.dao.objsql;

import com.db.manga.entity.objsql.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
