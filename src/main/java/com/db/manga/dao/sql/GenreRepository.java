package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Genre;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile({"sql", "objsql", "obj"})
public interface GenreRepository extends JpaRepository<Genre, Long> {
    Genre findByName(String name);
}
