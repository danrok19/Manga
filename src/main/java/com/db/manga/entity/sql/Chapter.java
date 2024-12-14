package com.db.manga.entity.sql;


import jakarta.persistence.*;

@Entity
@Table(name = "chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "episode_number")
    private int episodeNumber;

    @Column(name = "publication_date")
    private String publicationDate;

    @Column(name = "chapter_content")
    private String chapterContent;

    @ManyToOne(optional = false)
    @JoinColumn(name = "manga_id")
    private Manga manga;

    public Chapter() {}

    public Chapter(String title, int episodeNumber, String publicationDate, String chapterContent) {
        this.title = title;
        this.episodeNumber = episodeNumber;
        this.publicationDate = publicationDate;
        this.chapterContent = chapterContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
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
                ", title='" + title + '\'' +
                ", episodeNumber=" + episodeNumber +
                ", publicationDate='" + publicationDate + '\'' +
                ", chapterContent='" + chapterContent + '\'' +
                '}';
    }
}
