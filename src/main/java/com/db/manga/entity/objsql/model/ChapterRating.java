package com.db.manga.entity.objsql.model;


import com.db.manga.entity.objsql.types.RatingType;
import jakarta.persistence.*;

@Entity
@Table(name = "chapter_rating")
public class ChapterRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Embedded
    private RatingType ratingType;

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

    public ChapterRating(RatingType ratingType) {
        this.ratingType = ratingType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RatingType getRatingType() {
        return ratingType;
    }

    public void setRatingType(RatingType ratingType) {
        this.ratingType = ratingType;
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
                "ratingType=" + ratingType +
                ", id=" + id +
                '}';
    }
}