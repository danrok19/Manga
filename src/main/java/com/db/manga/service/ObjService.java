package com.db.manga.service;

import com.db.manga.entity.sql.*;

import java.util.List;

public interface ObjService {

    User getUserById(long id);

    void createUser(String userName, String password, String email, String signUpDate, List<Role> roles);

    void createRole(String roleDescription);

    void deleteUser(long id);

    List<Role> findAllRoles();

    Role findRoleByRoleDescription(String roleDescription);

    List<Manga> getAllMangas();

    List<Manga> getMangaByUserId(long userId);

    List<MangaRating> findAllMangaRatingByUserId(String userId);

    List<Subscription> findAllSubscriptionsByUserId(long userId);

    Manga getMangaById(Long mangaId);

    List<ChapterRating> findAllChapterRatingByMangaIdAndByUserId(long mangaId, long userId);

    void addChapterRating(long chapterId, long mangaId, long userId, int rating, String date);

    void addMangaRating(long mangaId, long userId, int rating, String date);

    void subscribeManga(String subscriptionDate, long mangaId, long userId);

    void unsubscribeManga(long id);

    void createChapter(String title, int episodeNumber, String publicationDate, String content, Long mangaId);

    List<Chapter> getChaptersByMangaId(long mangaId);

    List<Genre> findAllGenres();

    Genre findGenreByName(String name);

    void createManga(String title, String description, long authorId, List<Genre> genres);

    Chapter findChapterById(Long chapterId);

    void deleteChapter(Long chapterId);

    void deleteManga(Long mangaId);

}
