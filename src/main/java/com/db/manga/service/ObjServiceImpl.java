package com.db.manga.service;

import com.db.manga.dao.sql.*;
import com.db.manga.entity.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
@Profile("obj")
public class ObjServiceImpl implements ObjService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MangaRepository mangaRepository;
    private MangaRatingRepository mangaRatingRepository;
    private SubscriptionRepository subscriptionRepository;
    private ChapterRepository chapterRepository;
    private ChapterRatingRepository chapterRatingRepository;
    private GenreRepository genreRepository;

    @Autowired
    public ObjServiceImpl(UserRepository userRepository, RoleRepository roleRepository, MangaRepository mangaRepository, MangaRatingRepository mangaRatingRepository, SubscriptionRepository subscriptionRepository, ChapterRepository chapterRepository, ChapterRatingRepository chapterRatingRepository, GenreRepository genreRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.mangaRepository = mangaRepository;
        this.mangaRatingRepository = mangaRatingRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.chapterRepository = chapterRepository;
        this.chapterRatingRepository = chapterRatingRepository;
        this.genreRepository = genreRepository;
    }




    @Override
    public User getUserById(long id) {
        System.out.println("Searching for user...");
        Optional<User> result = userRepository.findById(id);
        User user = result.get();
        System.out.println("Found user: " + user);

        System.out.println("User's roles: " + user.getRoles());

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

    public void deleteUser(long id){
        System.out.println("Deleting user...");
        userRepository.deleteById(id);
        System.out.println("Successfully deleted user!");
    }


    @Override
    public void createRole(String roleDescription){
        System.out.println("Creating role...");
        Role role = new Role(roleDescription);
        System.out.println("Saving role: " + role);
        roleRepository.save(role);
        System.out.println("Successfully created role!");
    }

    @Override
    public List<Role> findAllRoles(){
        System.out.println("Searching for roles...");
        List<Role> roles = roleRepository.findAll();
        System.out.println("Found roles: " + roles);

        return roles;
    }


    @Override
    public Role findRoleByRoleDescription(String roleDescription){
        System.out.println("Searching for role...");
        Role role = roleRepository.findByRoleDescription(roleDescription);
        System.out.println("Found role: " + role);

        return role;
    }


    @Override
    public List<Manga> getAllMangas(){
        System.out.println("Searching for mangas...");
        List<Manga> result = mangaRepository.findAll();
        System.out.println("Found mangas: " + result);

        return result;
    }

    @Override
    public List<Manga> getMangaByUserId(long userId){
        System.out.println("Searching for mangas...");
        System.out.println("Searching for user...");
        Optional<User> result = userRepository.findById(userId);
        User user = result.get();

        List<Manga> mangas = mangaRepository.findByAutor(user);
        System.out.println("Found mangas: " + mangas);

        return mangas;
    }

    @Override
    public List<MangaRating> findAllMangaRatingByUserId(String userId){
        System.out.println("Searching for manga ratings...");
        long id = Long.parseLong(userId);
        List<MangaRating> mangaRatings = mangaRatingRepository.findByUserId(id);
        System.out.println("Found manga ratings: " + mangaRatings);

        return mangaRatings;
    }

    @Override
    public List<Subscription> findAllSubscriptionsByUserId(long userId){
        System.out.println("Searching for subscriptions...");
        List<Subscription> result = subscriptionRepository.findByUserId(userId);
        System.out.println("Found subscriptions: " + result);

        return result;
    }

    @Override
    public Manga getMangaById(Long mangaId){
        System.out.println("Searching for manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();
        System.out.println("Found manga: " + manga);


        return manga;
    }

    @Override
    public List<ChapterRating> findAllChapterRatingByMangaIdAndByUserId(long mangaId, long userId) {
        System.out.println("Searching for manga rating...");
        System.out.println("Searching for manga...");
        Optional<Manga> resultManga = mangaRepository.findById(mangaId);
        Manga manga = resultManga.get();
        System.out.println("Found mangaRating: " + manga);

        System.out.println("Searching for user...");
        Optional<User> resultUser = userRepository.findById(userId);
        User user = resultUser.get();
        System.out.println("Found user: " + user);

        System.out.println("Searching for chapter ratings...");
        List<ChapterRating> chapterRatings = chapterRatingRepository.findByMangaAndUser(manga, user);
        System.out.println("Found chapterRatings: " + chapterRatings);

        return chapterRatings;
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
    public List<Chapter> getChaptersByMangaId(long mangaId){
        System.out.println("Searching for chapters...");
        System.out.println("Searching for manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        Manga manga = result.get();

        List<Chapter> chapters = chapterRepository.findByManga(manga);
        System.out.println("Found chapters: " + chapters);

        return chapters;
    }

    @Override
    public List<Genre> findAllGenres() {
        System.out.println("Searching for all genres...");
        List<Genre> result = genreRepository.findAll();
        System.out.println("Found genres: " + result);

        return result;
    }

    @Override
    public Genre findGenreByName(String name){
        System.out.println("Searching for genre...");
        Optional<Genre> result = Optional.ofNullable(genreRepository.findByName(name));
        Genre genre = result.get();
        System.out.println("Found genre: " + genre);

        return genre;

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
        manga.setAutor(user);

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
    public Chapter findChapterById(Long chapterId) {
        System.out.println("Searching for chapter...");
        Optional<Chapter> result = chapterRepository.findById(chapterId);
        Chapter chapter = result.get();
        System.out.println("Found chapter: " + chapter);

        return chapter;
    }

    @Override
    public void deleteManga(Long mangaId) {
        System.out.println("Deleting manga...");
        Optional<Manga> result = mangaRepository.findById(mangaId);
        System.out.println("Found manga: " + result);
        Manga manga = result.get();

        System.out.println("Deleting all subscriptions...");
        subscriptionRepository.deleteByMangaId(manga.getId());
        System.out.println("Succesfully deleted subscriptions!");

        System.out.println("Deleting all chapters rating...");
        chapterRatingRepository.deleteAllByMangaId(manga.getId());

        System.out.println("Deleting all chapters...");
        chapterRepository.deleteByMangaId(manga.getId());

        System.out.println("Deleting all mangas rating...");
        mangaRatingRepository.deleteByMangaId(manga.getId());

        mangaRepository.delete(manga);
        System.out.println("Successfully deleted manga! HERE");
    }

    @Override
    public void deleteChapter(Long chapterId){
        System.out.println("Deleting chapter...");
        Optional<Chapter> result = chapterRepository.findById(chapterId);
        Chapter chapter = result.get();
        System.out.println("Found chapter: " + chapter);

        System.out.println("Deleting all chapters rating...");
        chapterRatingRepository.deleteAllByChapterId(chapter.getId());

        chapterRepository.deleteById(chapter.getId());
        System.out.println("Successfully deleted chapter!");
    }

}
