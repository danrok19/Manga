package com.db.manga.dao.objsql;

import com.db.manga.entity.objsql.model.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, Long> {
}
