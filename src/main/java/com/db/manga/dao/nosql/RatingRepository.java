package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Rating;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("nosql")
@Repository
public interface RatingRepository extends MongoRepository<Rating, String> {

    @Query("{ 'userId': ?0, 'chapterId': null }")
    List<Rating> findAllByUserIdAAndChapterIdNull(ObjectId userId);

    @Query(value = "{ 'userId': ?0, 'mangaId': ?1, 'chapterId': { $ne: null } }")
    List<Rating> findByUserIdAndMangaIdAndChapterIdNotNull(ObjectId userId, ObjectId mangaId);

    void deleteByChapterId(ObjectId chapterId);

    void deleteByMangaId(ObjectId mangaId);
}

