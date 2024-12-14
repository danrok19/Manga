package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
