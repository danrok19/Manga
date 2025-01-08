package com.db.manga.dao.sql;

import com.db.manga.entity.sql.Chapter;
import com.db.manga.entity.sql.Manga;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Profile({"sql", "objsql"})
public interface ChapterRepository extends JpaRepository<Chapter, Long> {

    List<Chapter> findByManga(Manga manga);


    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO "chapter" (chapter, manga_id)
        VALUES (ROW(:title, :episode_number, :publication_date, :chapter_content)::chapter_type, :manga_id)
    """, nativeQuery = true)
    void addChapter(@Param("title") String title,
                            @Param("episode_number") int episodeNumber,
                            @Param("publication_date") String publicationDate,
                            @Param("chapter_content") String chapterContent,
                            @Param("manga_id") Long mangaId);

}
