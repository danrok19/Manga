package com.db.manga.service;

import com.db.manga.entity.nosql.*;

import java.util.List;

public interface NoSqlService {
    User getUserById(String id);

    List<User> getAllUsers();

    void createUser(String userName, String password, String email, String signUpDate, List<String> roles);

    void updateUser(String id, String userName, String password, String email, String signUpDate);

    void deleteUser(String id);

    List<String> findAllRoles();

    void createManga(String title, String description, String authorId, List<String> genres);

    Manga getMangaById(String mangaId);

    //void createGenre(String name);

    //List<String> findAllGenres();

    //String findGenreById(long genreId);

    void createChapter(String title, int episodeNumber, String publicationDate, String content, String mangaId);

    Chapter findChapterById(String chapterId);

    void subscribeManga(String subscriptionDate, String mangaId, String userId);

    void unsubscribeManga(String id);

    void addMangaRating(String mangaId, String userId, int rating, String date);

    //MangaRating findMangaRatingById(long id);

    void changeMangaRating(String id, int rating, String date);

    void addChapterRating(String chapterId, String mangaId, String userId, int rating, String date);

    //ChapterRating findChapterRating(long id);

    void changeChapterRating(String id, int rating, String date);
}
