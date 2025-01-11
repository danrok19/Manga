package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Genre;
import com.db.manga.entity.sql.Manga;
import com.db.manga.entity.sql.User;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile({"sql", "objsql", "obj"})
public interface MangaRepository extends JpaRepository<Manga, Long> {

    List<Manga> findByAutor(User user);

    @Query("SELECT m FROM Manga m JOIN m.genres g WHERE g.id = :genreId")
    List<Manga> findByGenreId(@Param("genreId") Long genreId);

    List<Manga> findByTitleAndGenres_Id(String title, Long genreId);

    List<Manga> findByTitle(String title);

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



    @Modifying
    @Transactional
    @Query("DELETE FROM Manga m WHERE m.id = :mangaId")
    void deleteMangaById(@Param("mangaId") long mangaId);
}
