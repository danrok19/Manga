package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GenreRepository extends MongoRepository<Genre, String> {
}
