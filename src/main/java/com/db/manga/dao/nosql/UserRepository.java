package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
