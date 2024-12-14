package com.db.manga.dao.objsql;

import com.db.manga.entity.objsql.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChapterRepository extends JpaRepository<Chapter, Long> {
}
