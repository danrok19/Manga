package com.db.manga.entity.sql;

import jakarta.persistence.*;

@Entity
@Table(name = "manga_rating")
public class MangaRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @Column(name = "rating")
    private int rating;

    @Column(name = "date")
    private String date;

    public MangaRating() {}

    public MangaRating(int rating, String date) {
        this.rating = rating;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    @Override
    public String toString() {
        return "MangaRating{" +
                "id=" + id +
                ", rating=" + rating +
                ", date='" + date + '\'' +
                '}';
    }
}
