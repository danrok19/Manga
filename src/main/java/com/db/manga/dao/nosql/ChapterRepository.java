package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Chapter;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Profile("nosql")
@Repository
public interface ChapterRepository extends MongoRepository<Chapter, String> {
}
