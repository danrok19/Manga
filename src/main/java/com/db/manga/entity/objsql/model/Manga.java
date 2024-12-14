package com.db.manga.entity.objsql.model;


import com.db.manga.entity.objsql.types.MangaType;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "manga")
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Embedded
    private MangaType mangaType;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL)
    private List<Chapter> chapters;

    @ManyToOne(optional = false)
    @JoinColumn(name = "author_id")
    private User autor;

    @OneToMany(mappedBy = "manga", cascade = CascadeType.ALL)
    private List<Subscription> subscriptions;

    @ManyToMany()
    @JoinTable(
            name = "manga_genre",
            joinColumns = @JoinColumn(name = "manga_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @OneToMany(mappedBy = "manga")
    private List<MangaRating> mangaRatings;

    @OneToMany(mappedBy = "manga")
    private List<ChapterRating> chapterRatings;

    public Manga(){}

    public Manga(MangaType mangaType) {
        this.mangaType = mangaType;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MangaType getMangaType() {
        return mangaType;
    }

    public void setMangaType(MangaType mangaType) {
        this.mangaType = mangaType;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    public User getAutor() {
        return autor;
    }

    public void setAutor(User autor) {
        this.autor = autor;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<MangaRating> getMangaRatings() {
        return mangaRatings;
    }

    public void setMangaRatings(List<MangaRating> mangaRatings) {
        this.mangaRatings = mangaRatings;
    }

    public List<ChapterRating> getChapterRatings() {
        return chapterRatings;
    }

    public void setChapterRatings(List<ChapterRating> chapterRatings) {
        this.chapterRatings = chapterRatings;
    }

    @Override
    public String toString() {
        return "Manga{" +
                "id=" + id +
                ", mangaType=" + mangaType +
                '}';
    }
}
