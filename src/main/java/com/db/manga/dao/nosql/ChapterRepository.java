package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Chapter;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("nosql")
@Repository
public interface ChapterRepository extends MongoRepository<Chapter, String> {
    List<Chapter> findByMangaId(ObjectId mangaId);

    void deleteByMangaId(ObjectId mangaId);

}
