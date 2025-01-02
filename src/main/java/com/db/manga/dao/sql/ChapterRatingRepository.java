package com.db.manga.dao.sql;

import com.db.manga.entity.sql.ChapterRating;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Profile({"sql", "objsql"})
public interface ChapterRatingRepository extends JpaRepository<ChapterRating, Long> {

    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO "chapter_rating"(rating, user_id, chapter_id, manga_id)
        VALUES (ROW(:raitng_value, :date)::rating_type, :user_id, :chapter_id, :manga_id)
""", nativeQuery = true)
    void addRating(@Param("raitng_value") int rating,
                   @Param("date") String date,
                   @Param("chapter_id") long chapter_id,
                   @Param("user_id") long userId,
                   @Param("manga_id") long mangaId

    );

    @Modifying
    @Transactional
    @Query(value = """
        UPDATE "chapter_rating"
        SET rating = ROW(:raitng_value, :date)::rating_type
        WHERE id = :id
""", nativeQuery = true)
    void updateRating(@Param("id") long chapterRatingId,
                   @Param("raitng_value") int rating,
                   @Param("date") String date,
                   @Param("chapter_id") long chapter_id,
                   @Param("user_id") long userId,
                   @Param("manga_id") long mangaId

    );

}
