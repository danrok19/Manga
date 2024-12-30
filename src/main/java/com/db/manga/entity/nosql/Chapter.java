package com.db.manga.entity.nosql;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chapters")
public class Chapter {

    @Id
    private ObjectId id;

    private String title;
    private int episodeNumber;
    private String authorId;
    private ObjectId mangaId;
    private String content;

    public Chapter(){}

    public Chapter(String title, int episodeNumber, String content) {
        this.title = title;
        this.episodeNumber = episodeNumber;
        this.content = content;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public ObjectId getMangaId() {
        return mangaId;
    }

    public void setMangaId(ObjectId mangaId) {
        this.mangaId = mangaId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", authorId='" + authorId + '\'' +
                ", mangaId=" + mangaId +
                ", content='" + content + '\'' +
                '}';
    }
}
