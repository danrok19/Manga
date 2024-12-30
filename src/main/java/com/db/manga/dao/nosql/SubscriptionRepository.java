package com.db.manga.dao.nosql;

import com.db.manga.entity.nosql.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
}
