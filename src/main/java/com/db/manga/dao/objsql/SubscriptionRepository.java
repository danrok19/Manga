package com.db.manga.dao.objsql;

import com.db.manga.entity.objsql.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
