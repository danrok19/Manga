package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
}
