package com.db.manga.dao;

import com.db.manga.entity.sql.ChapterRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRatingRepository extends JpaRepository<ChapterRating, Long> {
}
