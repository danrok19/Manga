package com.db.manga.dao;

import com.db.manga.entity.sql.Manga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRepository extends JpaRepository<Manga, Long> {
}
