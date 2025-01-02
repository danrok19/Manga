package com.db.manga.service;

import com.db.manga.dao.sql.*;
import com.db.manga.entity.sql.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Profile("objsql")
public class ObjSqlServiceImpl implements ObjSqlService {
    private final MangaRatingRepository mangaRatingRepository;
    private final ChapterRatingRepository chapterRatingRepository;
    private GenreRepository genreRepository;
    private SubscriptionRepository subscriptionRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private MangaRepository mangaRepository;
    private DataSource dataSource;
    private ChapterRepository chapterRepository;

    @Autowired
    public ObjSqlServiceImpl(RoleRepository roleRepository, DataSource dataSource, UserRepository userRepository, GenreRepository genreRepository, MangaRepository mangaRepository, ChapterRepository chapterRepository, SubscriptionRepository subscriptionRepository, MangaRatingRepository mangaRatingRepository, ChapterRatingRepository chapterRatingRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.dataSource = dataSource;
        this.genreRepository = genreRepository;
        this.mangaRepository = mangaRepository;
        this.chapterRepository = chapterRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.mangaRatingRepository = mangaRatingRepository;
        this.chapterRatingRepository = chapterRatingRepository;
    }


    @Override
    public User getUserById(long id) {
        String sql = """
        SELECT 
            u.id, 
            u.user_data, 
            json_agg(json_build_object('id', r.id, 'roleDescription', r.role_description)) AS roles
        FROM 
            "user" u
        LEFT JOIN 
            user_role ur ON u.id = ur.user_id
        LEFT JOIN 
            role r ON ur.role_id = r.id
        WHERE 
            u.id = ?
        GROUP BY 
            u.id, u.user_data;
    """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mapowanie użytkownika
                long userId = rs.getLong("id");
                String userData = rs.getString("user_data");

                //System.out.println("To jest userdata: " + userData);

                userData = userData.replace("(", "");
                userData = userData.replace(")", "");

                // Zakładając, że user_data jest w formacie JSON lub rozdzielonym przecinkami
                String[] parts = userData.split(",");

                User user = new User();
                user.setId(userId);
                user.setUsername(parts[0].trim());
                user.setPassword(parts[1].trim());
                user.setEmail(parts[2].trim());
                user.setSignupDate(parts[3].trim());


                // Mapowanie ról z JSON
                String rolesJson = rs.getString("roles");
                if (rolesJson != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Role> roles = objectMapper.readValue(rolesJson, new TypeReference<List<Role>>() {});
                    user.setRoles(roles);
                }

                return user;
            } else {
                return null;  // Brak użytkownika
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // Obsługa błędów
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void createUser(String userName, String password, String email, String signUpDate, List<Role> roles) {
        System.out.println("Creating User...");


        System.out.println("Creating User class...");
        User user = new User(userName, password, email,  signUpDate);

        List<Long> rolesList = new ArrayList<>();

        for (Role role : roles) {
            System.out.println("Searching for role...");

            Optional<Role> roleFound = roleRepository.findById(role.getId());
            System.out.println("Found role: " + roleFound);

            user.add(roleFound.get());
            rolesList.add(roleFound.get().getId());
        }

        Long[] roleIdsArray = rolesList.toArray(new Long[0]);

        System.out.println("User's roles: " + user.getRoles());

        System.out.println("Saving User: " + user);
        userRepository.addUserWithRoles(user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getSignupDate(),
                roleIdsArray);
        System.out.println("Successfully created User!");
    }



    public void updateUser(long userId, String username, String password, String email, String signUpDate) {

        User userFound = getUserById(userId);

        System.out.println("Updating User...");
        System.out.println("User before: " + userFound);


        userRepository.updateUser(userId, username, password, email, signUpDate);

        User userFoundAfter = getUserById(userId);
        System.out.println("User after: " + userFoundAfter);
    }

    public void deleteUser(long userId) {
        System.out.println("Deleting User...");

        User user = getUserById(userId);

        System.out.println("User found: " + user);

        System.out.println("Deleting User...");
        userRepository.deleteUser(user.getId());
    }

    @Override
    public void createRole(String roleDescription) {
        System.out.println("Creating Role...");
        Role role = new Role(roleDescription);
        System.out.println("Saving Role: " + role);
        roleRepository.save(role);
        System.out.println("Successfully created Role!");
    }

    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }


    public void createManga(String title, String description, long authorId, List<Genre> genres){
        System.out.println("Creating Manga...");
        Manga manga = new Manga(title, description);

        System.out.println("Manga: " + manga);

        System.out.println("Searching author...");

        Optional<User> user = Optional.ofNullable(getUserById(authorId));

        System.out.println("User found: " + user);

        manga.setAutor(user.get());

        System.out.println("Searching for genres...");

        List<Long> genresList = new ArrayList<>();

        for (Genre genre : genres) {
            System.out.println("Searching for genre...");

            Optional<Genre> genreFound = genreRepository.findById(genre.getId());
            System.out.println("Found genre: " + genreFound);

            manga.add(genreFound.get());
            genresList.add(genreFound.get().getId());
        }


        Long[] genreIdsArray = genresList.toArray(new Long[0]);

        System.out.println("Manga's genres: " + manga.getGenres());


        System.out.println("Saving Manga: " + manga);
        mangaRepository.addMangaWithGenres(manga.getTitle(),
                manga.getDescription(),
                manga.getAutor().getId(),
                genreIdsArray);
        System.out.println("Successfully created Manga!");


    }

    public Manga getMangaById(Long id){
        String sql = """
        SELECT 
            m.id, 
            m.manga, 
            m.author_id,
            json_agg(json_build_object('id', g.id, 'name', g.name)) AS genres,
            u.user_data AS author_data
        FROM 
            "manga" m
        LEFT JOIN 
            manga_genre mg ON m.id = mg.manga_id
        LEFT JOIN 
            genre g ON mg.genre_id = g.id
        LEFT JOIN
            "user" u ON m.author_id = u.id
        WHERE 
            m.id = ?
        GROUP BY 
            m.id, m.manga, m.author_id, u.user_data;
    """;


        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Mapowanie manga
                long mangaId = rs.getLong("id");
                String mangaData = rs.getString("manga");
                long authorId = rs.getLong("author_id");

                //System.out.println("To jest userdata: " + userData);

                mangaData = mangaData.replace("(", "");
                mangaData = mangaData.replace(")", "");

                // Zakładając, że mangaData jest w formacie JSON lub rozdzielonym przecinkami
                String[] parts = mangaData.split(",");


                Manga manga = new Manga();
                manga.setId(mangaId);
                manga.setTitle(parts[0].trim().replace("\"", ""));
                manga.setDescription(String.join(",", Arrays.copyOfRange(parts, 1, parts.length)).trim().replace("\"", ""));


                // Mapowanie autora
                String authorData = rs.getString("author_data");
                if (authorData != null) {
                    authorData = authorData.replace("(", "");
                    authorData = authorData.replace(")", "");
                    String[] authorParts = authorData.split(",");

                    User author = new User();
                    author.setId(authorId);
                    author.setUsername(authorParts[0].trim());
                    author.setPassword(authorParts[1].trim());
                    author.setEmail(authorParts[2].trim());
                    author.setSignupDate(authorParts[3].trim());
                    manga.setAutor(author);
                }

                // Mapowanie genre z JSON
                String genresJson = rs.getString("genres");
                if (genresJson != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<Genre> genres = objectMapper.readValue(genresJson, new TypeReference<List<Genre>>() {});
                    manga.setGenres(genres);
                }

                return manga;
            } else {
                return null;  // Brak mangi
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // Obsługa błędów
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    public void createGenre(String name){
        System.out.println("Creating Genre...");
        Genre genre = new Genre(name);
        System.out.println("Saving Genre: " + genre);
        genreRepository.save(genre);
        System.out.println("Successfully created Genre!");
    }

    public List<Genre> findAllGenres(){
        System.out.println("Searching for all gernes...");

        List<Genre> genreList = genreRepository.findAll();
        System.out.println("Found gernes: " + genreList);

        return genreList;
    }

    public Genre findGenreById(long genreId){
        return genreRepository.findById(genreId).get();
    }

    public void createChapter(String title, int episodeNumber, String publicationDate, String content, Long mangaId){
        System.out.println("Creating Chapter...");
        Chapter chapter = new Chapter(title, episodeNumber, publicationDate, content);
        System.out.println("Creating Chapter: " + chapter);

        System.out.println("Searching for manga...");
        Optional<Manga> manga = Optional.ofNullable(getMangaById(mangaId));
        System.out.println("Found manga: " + manga);

        chapter.setManga(manga.get());

        System.out.println("Saving Chapter: " + chapter);
        chapterRepository.addChapter(chapter.getTitle(),
                chapter.getEpisodeNumber(),
                chapter.getPublicationDate(),
                chapter.getChapterContent(),
                chapter.getManga().getId()
        );
        System.out.println("Successfully created Chapter!");

    }

    public Chapter findChapterById(Long id){
        String sql = """
        SELECT
            c.id,
            c.chapter,
            c.manga_id,
            m.manga AS manga_data
            FROM chapter c
            LEFT JOIN
                manga m ON m.id = c.manga_id
            WHERE c.id = ?
            GROUP BY
                c.id, c.chapter, c.manga_id, m.manga;
                """;

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                //mapowanie chapter
                long chapterId = rs.getLong("id");
                String chapterData = rs.getString("chapter");
                Long mangaId = rs.getLong("manga_id");
                String mangaData = rs.getString("manga_data");

                chapterData = chapterData.replace("(", "");
                chapterData = chapterData.replace(")", "");

                String[] parts = chapterData.split(",");

                Chapter chapter = new Chapter();
                chapter.setId(chapterId);
                chapter.setTitle(parts[0].trim());
                chapter.setEpisodeNumber(Integer.parseInt(parts[1].trim()));
                chapter.setPublicationDate(parts[2].trim());
                chapter.setChapterContent(String.join(",", Arrays.copyOfRange(parts, 3, parts.length)).trim().replace("\"", ""));


                String[] partsManga = mangaData.split(",");
                //Mapowanie mangi
                Manga manga = new Manga();
                manga.setId(mangaId);
                manga.setTitle(partsManga[0].trim());
                manga.setDescription(partsManga[1].trim());

                chapter.setManga(manga);

                return chapter;

            }else{
                return null;
            }

        }catch (SQLException e) {
            e.printStackTrace();
            return null;  // Obsługa błędów
        }
    }

    public void subscribeManga(String subscriptionDate, long mangaId, long userId){
        System.out.println("Subscribing Manga...");
        Subscription subscription = new Subscription(subscriptionDate);

        System.out.println("Searching for Manga...");
        Optional<Manga> manga = Optional.ofNullable(getMangaById(mangaId));

        subscription.setManga(manga.get());

        System.out.println("Searching for User...");
        Optional<User> user = Optional.ofNullable(getUserById(userId));

        subscription.setUser(user.get());

        System.out.println("Saving Subscription: " + subscription);
        subscriptionRepository.save(subscription);
        System.out.println("Successfully subscribed Manga!");
    }

    public void unsubscribeManga(long id){
        System.out.println("Unsubscribing manga...");
        subscriptionRepository.findById(id);
        subscriptionRepository.deleteById(id);
        System.out.println("Successfully unsubscribed Manga!");
    }

    public void addMangaRating(long mangaId, long userId, int rating, String date){
        System.out.println("Adding Manga rating...");
        MangaRating mangaRating = new MangaRating(rating, date);

        System.out.println("Searching for Manga...");
        Optional<Manga> manga = Optional.ofNullable(getMangaById(mangaId));
        System.out.println("Found manga: " + manga);
        mangaRating.setManga(manga.get());

        System.out.println("Searching for User...");
        Optional<User> user = Optional.ofNullable(getUserById(userId));
        System.out.println("Found user: " + user);
        mangaRating.setUser(user.get());

        System.out.println("Saving Manga rating: " + mangaRating);
        mangaRatingRepository.addRating(
                mangaRating.getRating(),
                mangaRating.getDate(),
                mangaRating.getUser().getId(),
                mangaRating.getManga().getId()
        );

    }

    public MangaRating findMangaRatingById(long id){
        String sql = """
        SELECT 
            mr.id AS rating_id, 
            mr.rating, 
            mr.user_id,
            mr.manga_id,
            u.user_data AS user_data,
            m.manga AS manga_data
        FROM 
            "manga_rating" mr
        JOIN 
            "user" u ON mr.user_id = u.id
        JOIN 
            "manga" m ON mr.manga_id = m.id
        WHERE 
            mr.id = ?
    """;
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            MangaRating mangaRating = new MangaRating();

            if (rs.next()) {
                // Mapowanie manga_rating
                long mangaRatingId = rs.getLong("rating_id");
                String mangaRatingData = rs.getString("rating");
                long userId = rs.getLong("user_id");
                long mangaId = rs.getLong("manga_id");

                mangaRatingData = mangaRatingData.replace("(", "");
                mangaRatingData = mangaRatingData.replace(")", "");

                // Zakładając, że mangaData jest w formacie JSON lub rozdzielonym przecinkami
                String[] parts = mangaRatingData.split(",");

                mangaRating.setId(mangaRatingId);
                mangaRating.setRating(Integer.parseInt(parts[0].trim()));
                mangaRating.setDate(parts[1].trim());



                // Mapowanie user
                String userData = rs.getString("user_data");
                userData = userData.replace("(", "").replace(")", "");
                String[] userParts = userData.split(",");
                User user = new User();
                user.setId(userId);
                user.setUsername(userParts[0].trim());
                user.setPassword(userParts[1].trim());
                user.setEmail(userParts[2].trim());
                user.setSignupDate(userParts[3].trim());
                mangaRating.setUser(user);

                // Mapowanie manga
                String mangaData = rs.getString("manga_data");
                mangaData = mangaData.replace("(", "").replace(")", "");
                String[] mangaParts = mangaData.split(",");
                Manga manga = new Manga();
                manga.setId(mangaId);
                manga.setTitle(mangaParts[0].trim());
                manga.setDescription(String.join(",", Arrays.copyOfRange(mangaParts, 1, parts.length)).trim().replace("\"", ""));
                mangaRating.setManga(manga);

                return mangaRating;
            } else {
                return null;  // Brak mangi
            }


        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // Obsługa błędów
        }
    }

    public void changeMangaRating(long id, int rating, String date){
        System.out.println("Changing Manga rating...");

        System.out.println("Searching for MangaRating...");
        MangaRating mangaRating = findMangaRatingById(id);
        System.out.println("Found mangaRating: " + mangaRating);
        mangaRating.setRating(rating);
        mangaRating.setDate(date);
        System.out.println("Changed Manga rating: " + mangaRating);
        System.out.println("Saving Manga rating...");
        mangaRatingRepository.udpateRating(mangaRating.getId(),
                mangaRating.getRating(),
                mangaRating.getDate(),
                mangaRating.getUser().getId(),
                mangaRating.getManga().getId());
        System.out.println("Successfully changed Manga rating!");
    }


    public void addChapterRating(long chapterId, long mangaId, long userId, int rating, String date){
        System.out.println("Adding chapter rating...");
        ChapterRating chapterRating = new ChapterRating(rating, date);

        System.out.println("Searching for Chapter...");
        Chapter chapter = findChapterById(chapterId);
        System.out.println("Found chapter: " + chapter);
        chapterRating.setChapter(chapter);
        chapterRating.setManga(chapter.getManga());

        System.out.println("Found manga: " + chapter.getManga());

        System.out.println("Searching for User...");
        User user = getUserById(userId);
        System.out.println("Found user: " + user);
        chapterRating.setUser(user);

        System.out.println("Saving chapter rating...");
        chapterRatingRepository.addRating(
                chapterRating.getRating(),
                chapterRating.getDate(),
                chapterRating.getChapter().getId(),
                chapterRating.getUser().getId(),
                chapterRating.getManga().getId()
        );
        System.out.println("Successfully added chapter rating!");
    }

    public ChapterRating findChapterRating(long id){
        String sql = """
        SELECT
            cr.id AS rating_id,
            cr.rating,
            cr.user_id,
            cr.chapter_id,
            cr.manga_id,
            u.user_data AS user_data,
            c.chapter AS chapter_data,
            m.manga AS manga_data
        FROM 
            "chapter_rating" cr
        LEFT JOIN
            "user" u ON cr.user_id = u.id
        LEFT JOIN
            "chapter" c ON cr.chapter_id = c.id
        LEFT JOIN
            "manga" m ON cr.manga_id = m.id
        WHERE cr.id = ?;
        """;

        try(Connection conn = dataSource.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            ChapterRating chapterRating = new ChapterRating();

            if(rs.next()){
                //Mapowanie chapterRating
                long chatperRatingId = rs.getLong("rating_id");
                String chapterRatingData = rs.getString("rating");
                long userId = rs.getLong("user_id");
                long chapterId = rs.getLong("chapter_id");
                long mangaId = rs.getLong("manga_id");

                chapterRatingData = chapterRatingData.replace("(", "");
                chapterRatingData = chapterRatingData.replace(")", "");

                String[] parts = chapterRatingData.split(",");

                chapterRating.setId(chatperRatingId);
                chapterRating.setRating(Integer.parseInt(parts[0].trim()));
                chapterRating.setDate(parts[1].trim());

                //Mapowanie user
                String userData = rs.getString("user_data");
                userData = userData.replace("(", "").replace(")", "");
                String[] userParts = userData.split(",");
                User user = new User();
                user.setId(userId);
                user.setUsername(userParts[0].trim());
                user.setPassword(userParts[1].trim());
                user.setEmail(userParts[2].trim());
                user.setSignupDate(userParts[3].trim());
                chapterRating.setUser(user);


                //Mapowanie chapter
                String chapterData = rs.getString("chapter_data");
                chapterData = chapterData.replace("(", "").replace(")", "");
                String[] chapterParts = chapterData.split(",");
                System.out.println("chapterData: " + chapterData);
                Chapter chapter = new Chapter();
                chapter.setTitle(chapterParts[0].trim());
                chapter.setEpisodeNumber(Integer.parseInt(chapterParts[1].trim()));
                chapter.setPublicationDate(chapterParts[2].trim());
                //System.out.println("3: " + String.join(",", Arrays.copyOfRange(chapterParts, 3, chapterParts.length)).trim().replace("\"", ""));
                chapter.setChapterContent(String.join(",", Arrays.copyOfRange(chapterParts, 3, chapterParts.length)).trim().replace("\"", ""));
                chapterRating.setChapter(chapter);

                // Mapowanie manga
                String mangaData = rs.getString("manga_data");
                mangaData = mangaData.replace("(", "").replace(")", "");
                String[] mangaParts = mangaData.split(",");
                Manga manga = new Manga();
                manga.setId(mangaId);
                manga.setTitle(mangaParts[0].trim());
                manga.setDescription(String.join(",", Arrays.copyOfRange(mangaParts, 1, parts.length)).trim().replace("\"", ""));
                chapterRating.setManga(manga);

                return chapterRating;

            } else {
                return null;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;  // Obsługa błędów
        }

    }

    public void changeChapterRating(long id, int rating, String date){
        System.out.println("Changing chapter rating...");

        System.out.println("Searching for chapterRating...");
        ChapterRating chapterRating = findChapterRating(id);
        System.out.println("Found chapterRating: " + chapterRating);
        chapterRating.setRating(rating);
        chapterRating.setDate(date);
        System.out.println("Changed chapter rating: " + chapterRating);
        System.out.println("Saving chapter rating...");
        chapterRatingRepository.updateRating(
                chapterRating.getId(),
                chapterRating.getRating(),
                chapterRating.getDate(),
                chapterRating.getChapter().getId(),
                chapterRating.getUser().getId(),
                chapterRating.getManga().getId()
        );
        System.out.println("Successfully changed chapter rating!");

    }
}
