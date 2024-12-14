package com.db.manga.dao.objsql;

import com.db.manga.entity.objsql.model.MangaRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MangaRatingRepository extends JpaRepository<MangaRating, Long> {
}
