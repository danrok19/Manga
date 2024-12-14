package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, Long> {
}
