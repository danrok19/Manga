package com.db.manga.dao.sql;

import com.db.manga.entity.sql.MangaRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRatingRepository extends JpaRepository<MangaRating, Long> {
}
