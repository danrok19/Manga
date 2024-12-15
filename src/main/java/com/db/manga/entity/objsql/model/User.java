package com.db.manga.entity.objsql.model;

import com.db.manga.entity.objsql.types.UserType;
import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private com.db.manga.entity.objsql.types.UserType userData; // Zmieniamy na UserType jako embedded


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;


    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Manga> mangas;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "user")
    private List<MangaRating> mangaRatings;

    @OneToMany(mappedBy = "user")
    private List<ChapterRating> chapterRatings;


    public User() {}

    public User(UserType userData) {
        this.userData = userData;
    }


    public void add(Role role) {
        if(roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    public void add(Manga manga) {
        if(mangas == null) {
            mangas = new ArrayList<>();
        }
        mangas.add(manga);
    }

    public void add(Subscription subscription) {
        if(subscriptions == null) {
            subscriptions = new ArrayList<>();
        }
        subscriptions.add(subscription);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserType getUserData() {
        return userData;
    }

    public void setUserData(UserType userData) {
        this.userData = userData;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public List<Manga> getMangas() {
        return mangas;
    }

    public void setMangas(List<Manga> mangas) {
        this.mangas = mangas;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
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
        return "User{" +
                "id=" + id +
                ", userType=" + userData +
                '}';
    }
}
