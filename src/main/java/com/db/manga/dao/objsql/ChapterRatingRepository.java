package com.db.manga.dao.objsql;

import com.db.manga.entity.objsql.model.ChapterRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRatingRepository extends JpaRepository<ChapterRating, Long> {
}
