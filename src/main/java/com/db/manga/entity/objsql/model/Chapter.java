package com.db.manga.entity.objsql.model;

import com.db.manga.entity.objsql.types.ChapterType;
import com.db.manga.entity.sql.Manga;
import jakarta.persistence.*;

@Entity
@Table(name = "chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Embedded
    private ChapterType chapterType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manga_id")
    private Manga manga;

    public Chapter() {}

    public Chapter(ChapterType chapterType) {
        this.chapterType = chapterType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ChapterType getChapterType() {
        return chapterType;
    }

    public void setChapterType(ChapterType chapterType) {
        this.chapterType = chapterType;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", chapterType=" + chapterType +
                '}';
    }
}