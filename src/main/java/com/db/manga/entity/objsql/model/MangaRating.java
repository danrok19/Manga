package com.db.manga.entity.objsql.model;

import com.db.manga.entity.objsql.types.RatingType;
import jakarta.persistence.*;

@Entity
@Table(name = "manga_rating")
public class MangaRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne()
    @JoinColumn(name = "manga_id")
    private Manga manga;

    @Embedded
    private RatingType ratingType;

    public MangaRating() {}

    public MangaRating(RatingType ratingType) {
        this.ratingType = ratingType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public RatingType getMangaType() {
        return ratingType;
    }

    public void setMangaType(RatingType ratingType) {
        this.ratingType = ratingType;
    }

    @Override
    public String toString() {
        return "MangaRating{" +
                "id=" + id +
                ", ratingType=" + ratingType +
                '}';
    }
}