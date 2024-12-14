package com.db.manga.entity.sql;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "manga")
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

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

    public Manga() {}

    public Manga(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void add(Chapter chapter) {
        if(chapters == null) {
            chapters = new ArrayList<>();
        }
        chapters.add(chapter);
    }

    public void add(Subscription subscription) {
        if(subscriptions == null) {
            subscriptions = new ArrayList<>();
        }
        subscriptions.add(subscription);
    }

    public void add(Genre genre) {
        if(genres == null) {
            genres = new ArrayList<>();
        }
        genres.add(genre);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "Manga{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
