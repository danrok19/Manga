package com.db.manga.entity.nosql;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document( collection = "mangas")
public class Manga {

    @Id
    private ObjectId id;

    private String title;
    private List<String> genre;
    private String description;
    private ObjectId authorId;
    private List<ObjectId> chapters;


    public Manga(){}

    public Manga(String title, List<String> genre, String description) {
        this.title = title;
        this.genre = genre;
        this.description = description;
    }

    public void add(Chapter chapter){
        if(chapters == null){
            chapters = new ArrayList<>();
        }
        chapters.add(chapter.getId());
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

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ObjectId getAuthorId() {
        return authorId;
    }

    public void setAuthorId(ObjectId authorId) {
        this.authorId = authorId;
    }

    public List<ObjectId> getChapters() {
        return chapters;
    }

    public void setChapters(List<ObjectId> chapters) {
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return "Manga{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                ", description='" + description + '\'' +
                ", authorId=" + authorId +
                ", chapters=" + chapters +
                '}';
    }
}
