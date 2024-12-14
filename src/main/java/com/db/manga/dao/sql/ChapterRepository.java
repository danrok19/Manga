package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
