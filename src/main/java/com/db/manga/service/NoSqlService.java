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

    List<Manga> getAllMangas();

    void deleteManga(String mangaId);

    void createGenre(String name);

    List<Genre> findAllGenres();

    Genre findGenreById(String genreId);

    void createChapter(String title, int episodeNumber, String publicationDate, String content, String mangaId);

    Chapter findChapterById(String chapterId);

    List<Chapter> getChaptersByMangaId(String mangaId);

    void deleteChapter(String chapterId);

    void subscribeManga(String subscriptionDate, String mangaId, String userId);

    List<Subscription> findAllSubscriptionsByUserId(String userId);

    void unsubscribeManga(String id, String userId);

    void addMangaRating(String mangaId, String userId, int rating, String date);

    List<Rating> findAllMangaRatingByUserId(String userId);

    //MangaRating findMangaRatingById(long id);

    void changeMangaRating(String id, int rating, String date);

    void addChapterRating(String chapterId, String mangaId, String userId, int rating, String date);

    List<Rating> findAllChapterRatingByMangaIdAndByUserId(String mangaId, String userId);

    //ChapterRating findChapterRating(long id);

    void changeChapterRating(String id, int rating, String date);
}
