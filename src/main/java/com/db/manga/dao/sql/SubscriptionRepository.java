package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Subscription;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile({"sql", "objsql", "obj"})
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUserId(Long userId);

    @Transactional
    void deleteByMangaId(Long mangaId);
}
