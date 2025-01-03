package com.db.manga.service;

import com.db.manga.dao.nosql.*;
import com.db.manga.entity.nosql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("nosql")
public class NoSqlServiceImpl implements NoSqlService {

    private UserRepository userRepository;
    private SubscriptionRepository subscriptionRepository;
    private RatingRepository ratingRepository;
    private MangaRepository mangaRepository;
    private ChapterRepository chapterRepository;

    @Autowired
    public NoSqlServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository, RatingRepository ratingRepository, MangaRepository mangaRepository, ChapterRepository chapterRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.ratingRepository = ratingRepository;
        this.mangaRepository = mangaRepository;
        this.chapterRepository = chapterRepository;
    }

    @Override
    public User getUserById(String id) {
        System.out.println("Searching for user...");
        Optional<User> result = userRepository.findById(id);
        User user = result.get();
        System.out.println("Found user: " + user);

        return user;
    }

    public List<User> getAllUsers(){
        System.out.println("Searching for all users...");
        List<User> result = userRepository.findAll();
        System.out.println("Found users: " + result);

        return result;
    }

    @Override
    public void createUser(String userName, String password, String email, String signUpDate, List<String> roles) {
        System.out.println("Creating user...");
        User user = new User(userName, password, email, signUpDate);
        user.setRoles(roles);
        System.out.println("Saving user: " + user);
        userRepository.save(user);
        System.out.println("Successfully created user!");
    }


    @Override
    public void updateUser(String id, String userName, String password, String email, String signUpDate) {
        System.out.println("Updating user...");
        Optional<User> result = userRepository.findById(id);
        User user = result.get();
        System.out.println("Found user: " + user);

        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);

        System.out.println("Saving user: " + user);
        userRepository.save(user);
        System.out.println("Successfully updated user!");
    }

    @Override
    public void deleteUser(String id) {
        System.out.println("Deleting user...");

        System.out.println("Searching for user...");
        Optional<User> result = userRepository.findById(id);
        User user = result.get();
        System.out.println("Found user: " + user);

        System.out.println("Deleting user...");
        userRepository.delete(user);
        System.out.println("Successfully deleted user!");

    }

    @Override
    public List<String> findAllRoles() {
        return List.of();
    }

    @Override
    public void createManga(String title, String description, String authorId, List<String> genres) {
        System.out.println("Creating manga...");
        Manga manga = new Manga(title, description, authorId, genres);
        System.out.println("Searching for user...");
        Optional<User> result = userRepository.findById(authorId);
        User user = result.get();
        System.out.println("Found user: " + user);

        System.out.println("Saving manga: " + manga);
        mangaRepository.save(manga);
        System.out.println("Successfully created manga!");
    }

    @Override
    public Manga getMangaById(String mangaId) {
        System.out.println("Searching for Manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();
        System.out.println("Found manga: " + manga);

        return manga;
    }

//    @Override
//    public void createGenre(String name) {
//
//    }

//    @Override
//    public List<String> findAllGenres() {
//        return List.of();
//    }

//    @Override
//    public String findGenreById(long genreId) {
//        return null;
//    }

    @Override
    public void createChapter(String title, int episodeNumber, String publicationDate, String content, String mangaId) {
        System.out.println("Creating chapter...");
        Chapter chapter = new Chapter(title, episodeNumber, content, publicationDate);

        System.out.println("Searching for manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();
        System.out.println("Found manga: " + manga);
        chapter.setMangaId(manga.getId());

        System.out.println("Saving chapter: " + chapter);
        chapterRepository.save(chapter);
        System.out.println("Successfully created chapter!");

    }

    @Override
    public Chapter findChapterById(String chapterId) {
        System.out.println("Searching for chapter...");
        Optional<Chapter> result = chapterRepository.findById(chapterId);
        Chapter chapter = result.get();

        return chapter;
    }

    @Override
    public void subscribeManga(String subscriptionDate, String mangaId, String userId) {
        System.out.println("Subscribing manga...");
        Subscription subscription = new Subscription(subscriptionDate);

        System.out.println("Searching for User...");
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
        System.out.println("Found user: " + user);

        System.out.println("Searching for manga...");
        Optional<Manga> result1 = mangaRepository.findById(mangaId);
        Manga manga = result1.get();
        System.out.println("Found manga: " + manga);

        subscription.setUserId(user.getId());
        subscription.setMangaId(manga.getId());

        System.out.println("Saving subscription: " + subscription);
        subscriptionRepository.save(subscription);
        System.out.println("Successfully subscribed manga!");

    }

    @Override
    public void unsubscribeManga(String id) {
        System.out.println("Searching for subscription...");
        Optional<Subscription> subscription = subscriptionRepository.findById(id);
        System.out.println("found subscription: " + subscription);

        System.out.println("Deleting subscription...");
        subscriptionRepository.delete(subscription.get());
        System.out.println("Successfully deleted subscription!");

    }

    @Override
    public void addMangaRating(String mangaId, String userId, int rating, String date) {
        System.out.println("Adding manga rating...");
        System.out.println("Searching for manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();
        System.out.println("Found manga: " + manga);

        System.out.println("Searching for user...");
        Optional<User> result1 = userRepository.findById(userId);
        User user = result1.get();
        System.out.println("Found user: " + user);

        Rating mangaRating = new Rating(rating, "Manga", date);
        mangaRating.setUserId(String.valueOf(user.getId()));
        mangaRating.setMangaId(String.valueOf(manga.getId()));

        System.out.println("Saving rating: " + mangaRating);
        ratingRepository.save(mangaRating);
        System.out.println("Successfully added rating!");

    }

//    @Override
//    public MangaRating findMangaRatingById(long id) {
//        return null;
//    }

    @Override
    public void changeMangaRating(String id, int rating, String date) {
        System.out.println("Changing manga rating...");
        System.out.println("Searching for manga rating...");
        Optional<Rating> result = ratingRepository.findById(id);
        Rating mangaRating = result.get();
        System.out.println("Found manga: " + mangaRating);

        mangaRating.setRatingValue(rating);
        System.out.println("Saving rating: " + mangaRating);
        ratingRepository.save(mangaRating);
        System.out.println("Successfully changed rating!");
    }

    @Override
    public void addChapterRating(String chapterId, String mangaId, String userId, int rating, String date) {
        System.out.println("Adding chapter rating...");
        Rating chapterRating = new Rating(rating, "Chapter", date);

        System.out.println("Searching for manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();
        System.out.println("Found manga: " + manga);
        chapterRating.setMangaId(mangaId);

        System.out.println("Searching for chapter...");
        Optional<Chapter> resultChapter = chapterRepository.findById(chapterId);
        Chapter chapter = resultChapter.get();
        System.out.println("Found chapter: " + chapter);
        chapterRating.setChapterId(chapterId);

        System.out.println("Searching for user...");
        Optional<User> resultUser = userRepository.findById(userId);
        User user = resultUser.get();
        System.out.println("Found user: " + user);
        chapterRating.setUserId(String.valueOf(user.getId()));

        System.out.println("Saving chapter: " + chapterRating);
        ratingRepository.save(chapterRating);
        System.out.println("Successfully added chapter!");
    }

//    @Override
//    public ChapterRating findChapterRating(long id) {
//        return null;
//    }

    @Override
    public void changeChapterRating(String id, int rating, String date) {
        System.out.println("Changing chapter rating...");
        System.out.println("Searching for chapter rating...");
        Optional<Rating> result = ratingRepository.findById(id);
        Rating chapterRating = result.get();
        System.out.println("Found manga rating: " + chapterRating);

        chapterRating.setRatingValue(rating);

        System.out.println("Saving rating: " + chapterRating);
        ratingRepository.save(chapterRating);
        System.out.println("Successfully changed chapter!");

    }
}
