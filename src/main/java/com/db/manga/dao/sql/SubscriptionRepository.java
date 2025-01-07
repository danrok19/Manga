package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Manga;
import com.db.manga.entity.sql.Subscription;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Profile({"sql", "objsql"})
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);

    void deleteByManga(Manga manga);
}
