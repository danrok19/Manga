package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Subscription;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

@Profile({"sql", "objsql"})
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
