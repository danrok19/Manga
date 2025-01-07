package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Manga;
import com.db.manga.entity.sql.MangaRating;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile({"sql", "objsql"})
public interface MangaRatingRepository extends JpaRepository<MangaRating, Long> {


    List<MangaRating> findByUserId(long userId);

    void deleteByManga(Manga manga);

    @Modifying
    @Transactional
    @Query(value = """
            INSERT INTO "manga_rating"(rating, user_id, manga_id)
            VALUES (ROW(:raitng_value, :date)::rating_type, :user_id, :manga_id)
    """, nativeQuery = true)
    void addRating(@Param("raitng_value") int rating,
                   @Param("date") String date,
                   @Param("user_id") long userId,
                   @Param("manga_id") long mangaId
                   );

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE "manga_rating"
        SET rating = ROW(:raitng_value, :date)::rating_type
        WHERE id = :id AND user_id = :user_id AND manga_id = :manga_id
    """, nativeQuery = true
    )
    void udpateRating(@Param("id") long mangaRatingId,
            @Param("raitng_value") int rating,
            @Param("date") String date,
            @Param("user_id") long userId,
            @Param("manga_id") long mangaId
    );
}
