package com.db.manga.service;

import com.db.manga.dao.sql.*;
import com.db.manga.entity.sql.*;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Profile("sql")
public class SqlServiceImpl implements SqlService{

    private UserRepository userRepository;
    private SubscriptionRepository subscriptionRepository;
    private MangaRepository mangaRepository;
    private RoleRepository roleRepository;
    private MangaRatingRepository mangaRatingRepository;
    private GenreRepository genreRepository;
    private ChapterRepository chapterRepository;
    private ChapterRatingRepository chapterRatingRepository;

    @Autowired
    public SqlServiceImpl(UserRepository userRepository, SubscriptionRepository subscriptionRepository, MangaRepository mangaRepository, RoleRepository roleRepository, MangaRatingRepository mangaRatingRepository, GenreRepository genreRepository, ChapterRepository chapterRepository, ChapterRatingRepository chapterRatingRepository) {
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.mangaRepository = mangaRepository;
        this.roleRepository = roleRepository;
        this.mangaRatingRepository = mangaRatingRepository;
        this.genreRepository = genreRepository;
        this.chapterRepository = chapterRepository;
        this.chapterRatingRepository = chapterRatingRepository;
    }



    @Override
    public User getUserById(long id) {
        System.out.println("Searching for user...");
        Optional<User> result = userRepository.findById(id);
        User user = result.get();
        System.out.println("Found user: " + user);

        return user;
    }

    @Override
    public void createUser(String userName, String password, String email, String signUpDate, List<Role> roles) {
        System.out.println("Creating user...");
        User user = new User(userName, password, email, signUpDate);
        user.setRoles(roles);
        System.out.println("Saving user: " + user);
        userRepository.save(user);
        System.out.println("Successfully created user!");
    }

    @Override
    public void createRole(String roleDescription) {
        System.out.println("Creating role...");
        Role role = new Role(roleDescription);
        System.out.println("Saving role: " + role);
        roleRepository.save(role);
        System.out.println("Successfully created role!");
    }

    @Override
    public void updateUser(long id, String userName, String password, String email, String signUpDate) {
        System.out.println("Updating user...");
        System.out.println("Searching for user...");
        Optional<User> result = userRepository.findById(id);
        User user = result.get();
        System.out.println("Found user: " + user);
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        System.out.println("Changing user: " + user);
        userRepository.save(user);
        System.out.println("Successfully updated user!");
    }

    @Override
    public void deleteUser(long id) {
        System.out.println("Deleting user...");
        Optional<User> result = userRepository.findById(id);
        User user = result.get();
        System.out.println("Found user: " + user);
        userRepository.delete(user);
        System.out.println("Successfully deleted user!");
    }

    @Override
    public List<Role> findAllRoles() {
        System.out.println("Searching for all roles...");
        List<Role> result = roleRepository.findAll();
        System.out.println("Found roles: " + result);

        return result;
    }

    @Override
    public void createManga(String title, String description, long authorId, List<Genre> genres) {
        System.out.println("Creating manga...");
        Manga manga = new Manga();
        manga.setTitle(title);
        manga.setDescription(description);

        Optional<User> result = userRepository.findById(authorId);
        User user = result.get();
        System.out.println("Found user: " + user);

        System.out.println("Searching for genres...");
        for (Genre genre : genres) {
            System.out.println("Searching for genre...");

            Optional<Genre> genreFound = genreRepository.findById(genre.getId());
            System.out.println("Found genre: " + genreFound);

            manga.add(genreFound.get());
        }

        System.out.println("Saving manga: " + manga);
        mangaRepository.save(manga);
        System.out.println("Successfully created manga!");
    }

    @Override
    public Manga getMangaById(Long mangaId) {
        System.out.println("Searching for manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();
        System.out.println("Found manga: " + manga);

        return manga;
    }

    @Override
    public void createGenre(String name) {
        System.out.println("Creating genre...");
        Genre genre = new Genre(name);
        System.out.println("Saving genre: " + genre);
        genreRepository.save(genre);
        System.out.println("Successfully created genre!");
    }

    @Override
    public List<Genre> findAllGenres() {
        System.out.println("Searching for all genres...");
        List<Genre> result = genreRepository.findAll();
        System.out.println("Found genres: " + result);

        return result;
    }

    @Override
    public Genre findGenreById(long genreId) {
        System.out.println("Searching for genre...");
        Optional<Genre> result = genreRepository.findById(genreId);
        Genre genre = result.get();
        System.out.println("Found genre: " + genre);

        return genre;
    }

    @Override
    public void createChapter(String title, int episodeNumber, String publicationDate, String content, Long mangaId) {
        System.out.println("Creating chapter...");
        Chapter chapter = new Chapter(title, episodeNumber, publicationDate, content);
        System.out.println("Chapter: " + chapter);

        System.out.println("Searching for manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();
        System.out.println("Found manga: " + manga);
        chapter.setManga(manga);

        System.out.println("Saving chapter: " + chapter);
        chapterRepository.save(chapter);
        System.out.println("Successfully created chapter!");
    }

    @Override
    public Chapter findChapterById(Long chapterId) {
        System.out.println("Searching for chapter...");
        Optional<Chapter> result = chapterRepository.findById(chapterId);
        Chapter chapter = result.get();
        System.out.println("Found chapter: " + chapter);

        return chapter;
    }

    @Override
    public void subscribeManga(String subscriptionDate, long mangaId, long userId) {
        System.out.println("Subscribing to manga...");
        Subscription subscription = new Subscription(subscriptionDate);

        System.out.println("Searching for manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();
        System.out.println("Found manga: " + manga);

        System.out.println("Searching for user...");
        Optional<User> resultUser = userRepository.findById(userId);
        User user = resultUser.get();
        System.out.println("Found user: " + user);

        subscription.setUser(user);
        subscription.setManga(manga);

        System.out.println("Saving subscription: " + subscription);
        subscriptionRepository.save(subscription);
        System.out.println("Successfully saved subscription!");

    }

    @Override
    public void unsubscribeManga(long id) {
        System.out.println("Unsubscribing manga...");
        subscriptionRepository.findById(id);
        subscriptionRepository.deleteById(id);
        System.out.println("Successfully unsubscribed manga!");

    }

    @Override
    public void addMangaRating(long mangaId, long userId, int rating, String date) {
        System.out.println("Adding manga rating...");
        MangaRating mangaRating = new MangaRating(rating, date);

        System.out.println("Searching for manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();
        System.out.println("Found manga: " + manga);
        mangaRating.setManga(manga);

        System.out.println("Searching for user...");
        Optional<User> resultUser = userRepository.findById(userId);
        User user = resultUser.get();
        System.out.println("Found user: " + user);
        mangaRating.setUser(user);

        System.out.println("Saving manga rating: " + mangaRating);
        mangaRatingRepository.save(mangaRating);
        System.out.println("Successfully added manga rating!");

    }

    @Override
    public MangaRating findMangaRatingById(long id) {
        System.out.println("Searching for manga rating...");
        Optional<MangaRating> result = mangaRatingRepository.findById(id);
        MangaRating mangaRating = result.get();
        System.out.println("Found mangaRating: " + mangaRating);

        return mangaRating;
    }

    @Override
    public void changeMangaRating(long id, int rating, String date) {
        System.out.println("Changing manga rating...");
        System.out.println("Searching for manga rating...");
        Optional<MangaRating> result = mangaRatingRepository.findById(id);
        MangaRating mangaRating = result.get();
        System.out.println("Found mangaRating: " + mangaRating);

        mangaRating.setRating(rating);
        mangaRating.setDate(date);
        System.out.println("Saving manga rating: " + mangaRating);
        mangaRatingRepository.save(mangaRating);
        System.out.println("Successfully changed manga rating!");
    }

    @Override
    public void addChapterRating(long chapterId, long mangaId, long userId, int rating, String date) {
        System.out.println("Adding chapter rating...");
        ChapterRating chapterRating = new ChapterRating(rating, date);
        System.out.println("Searching for user...");
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();
        System.out.println("Found user: " + user);
        chapterRating.setUser(user);

        System.out.println("Searching for chapter...");
        Optional<Chapter> resultChapter = chapterRepository.findById(chapterId);
        Chapter chapter = resultChapter.get();
        System.out.println("Found chapter: " + chapter);
        chapterRating.setChapter(chapter);

        System.out.println("Searching for manga...");
        Optional<Manga> resultManga = mangaRepository.findById(mangaId);
        Manga manga = resultManga.get();
        System.out.println("Found manga: " + manga);
        chapterRating.setManga(manga);

        System.out.println("Saving chapter rating: " + chapterRating);
        chapterRatingRepository.save(chapterRating);
        System.out.println("Successfully added chapter rating!");

    }

    @Override
    public ChapterRating findChapterRating(long id) {
        System.out.println("Searching for chapter rating...");
        Optional<ChapterRating> result = chapterRatingRepository.findById(id);
        ChapterRating chapterRating = result.get();
        System.out.println("Found chapterRating: " + chapterRating);

        return chapterRating;
    }

    @Override
    public void changeChapterRating(long id, int rating, String date) {
        System.out.println("Changing chapter rating...");
        System.out.println("Searching for chapter rating...");
        Optional<ChapterRating> result = chapterRatingRepository.findById(id);
        ChapterRating chapterRating = result.get();
        System.out.println("Found chapterRating: " + chapterRating);

        chapterRating.setRating(rating);
        chapterRating.setDate(date);

        System.out.println("Saving chapter rating: " + chapterRating);
        chapterRatingRepository.save(chapterRating);
        System.out.println("Successfully changed chapter rating!");
    }
}
