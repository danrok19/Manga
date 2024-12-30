package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Manga;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MangaRepository extends MongoRepository<Manga, String> {
}
