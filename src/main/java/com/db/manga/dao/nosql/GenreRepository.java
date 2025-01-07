package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Genre;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

@Profile("nosql")
public interface GenreRepository extends MongoRepository<Genre, String> {
}
