package com.db.manga.service;

import com.db.manga.entity.sql.*;

import java.util.List;

public interface ObjSqlService {

    User getUserById(long id);

    void createUser(String userName, String password, String email, String signUpDate, List<Role> roles);

    void createRole(String roleDescription);

    void updateUser(long id, String userName, String password, String email, String signUpDate);

    void deleteUser(long id);

    List<Role> findAllRoles();

    void createManga(String title, String description, long authorId, List<Genre> genres);

    Manga getMangaById(Long mangaId);

    void createGenre(String name);

    List<Genre> findAllGenres();

    Genre findGenreById(long genreId);

    void createChapter(String title, int episodeNumber, String publicationDate, String content, Long mangaId);

    Chapter findChapterById(Long chapterId);

    void subscribeManga(String subscriptionDate, long mangaId, long userId);

    void unsubscribeManga(long id);

    void addMangaRating(long mangaId, long userId, int rating, String date);

    MangaRating findMangaRatingById(long id);

    void changeMangaRating(long id, int rating, String date);

    void addChapterRating(long chapterId, long mangaId, long userId, int rating, String date);

    ChapterRating findChapterRating(long id);

    void changeChapterRating(long id, int rating, String date);
}
