package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Chapter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChapterRepository extends MongoRepository<Chapter, String> {
}
