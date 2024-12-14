package com.db.manga.dao;

import com.db.manga.entity.sql.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
