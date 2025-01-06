package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Manga;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("nosql")
@Repository
public interface MangaRepository extends MongoRepository<Manga, String> {
    List<Manga> findByAuthorId(ObjectId userId);
}
