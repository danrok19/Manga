package com.db.manga.entity.sql;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "signup_date")
    private String signupDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Manga> mangas;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true )
    private List<Subscription> subscriptions;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<MangaRating> mangaRatings;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ChapterRating> chapterRatings;

    public User() {}

    public User(String username, String password, String email, String signupDate) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.signupDate = signupDate;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(String signupDate) {
        this.signupDate = signupDate;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", signupDate='" + signupDate + '\'' +
                '}';
    }
}
