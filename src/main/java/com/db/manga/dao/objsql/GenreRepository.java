package com.db.manga.dao.objsql;

import com.db.manga.entity.objsql.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
