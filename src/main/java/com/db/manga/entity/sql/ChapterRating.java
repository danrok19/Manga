package com.db.manga.entity.sql;

import jakarta.persistence.*;

@Entity
@Table(name = "chapter_rating")
public class ChapterRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "rating")
    private int rating;

    @Column(name = "date")
    private String date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    public ChapterRating() {}

    public ChapterRating(int rating, String date) {
        this.rating = rating;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Manga getManga() {
        return manga;
    }

    public void setManga(Manga manga) {
        this.manga = manga;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return "ChapterRating{" +
                "rating=" + rating +
                ", date='" + date + '\'' +
                ", id=" + id +
                '}';
    }
}
