package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Manga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface MangaRepository extends JpaRepository<Manga, Long> {


    @Modifying
    @Transactional
    @Query(value = """
        WITH inserted_manga AS (
            INSERT INTO "manga" (manga, author_id)
            VALUES (ROW(:title, :description)::manga_type, :authorId)
            RETURNING id
        )
        INSERT INTO "manga_genre" (manga_id, genre_id)
        SELECT (SELECT id FROM inserted_manga), unnest(:genreIds);
    """, nativeQuery = true)
    void addMangaWithGenres(@Param("title") String title,
                            @Param("description") String description,
                            @Param("authorId") long authorId,
                            @Param("genreIds") Long[] genreIds);
}
