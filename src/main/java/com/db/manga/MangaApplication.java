package com.db.manga;

import com.db.manga.dao.*;
import com.db.manga.entity.sql.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class MangaApplication {

	private final MangaRepository mangaRepository;

	public MangaApplication(MangaRepository mangaRepository) {
		this.mangaRepository = mangaRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MangaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(SqlDao sqlDao, UserRepository userRepository, GenreRepository genreRepository, MangaRepository mangaRepository, RoleRepository roleRepository, ChapterRepository chapterRepository, SubscriptionRepository subscriptionRepository, MangaRatingRepository mangaRatingRepository, ChapterRatingRepository chapterRatingRepository) {

		return runner -> {

			//createUserWitchJpa(userRepository, roleRepository);

			//createMangaWithJpa(userRepository, genreRepository, mangaRepository);

			//createRole(roleRepository);

			//createChapter(chapterRepository);

			//createSubscription(subscriptionRepository, userRepository, mangaRepository);

			//createMangaRating(mangaRatingRepository, userRepository, mangaRepository);

			//deleteSubscription(subscriptionRepository,  userRepository, mangaRepository);

			//createChapterRating(chapterRatingRepository, userRepository, mangaRepository, chapterRepository);

			//deleteUser(userRepository);
		};

	}

	private void deleteUser(UserRepository userRepository) {
		System.out.println("Deleting User....");
		long userId = 7;
		Optional<User> userFound = userRepository.findById(userId);
		System.out.println("User found: " + userFound);

		userRepository.deleteById(userFound.get().getId());

		System.out.println("Successfully deleted User!");
	}

	private void createChapterRating(ChapterRatingRepository chapterRatingRepository, UserRepository userRepository, MangaRepository mangaRepository, ChapterRepository chapterRepository) {

		System.out.println("Creating Chapter Rating...");

		System.out.println("Searching for Chapter...");
		long chapterId = 1;
		Optional<Chapter> chapterFound = chapterRepository.findById(chapterId);
		System.out.println("Found chapter: " + chapterFound);

		System.out.println("Searching for Manga of the Chapter...");
		Optional<Manga> mangaFound = mangaRepository.findById(chapterFound.get().getManga().getId());
		System.out.println("Found manga: " + mangaFound);

		System.out.println("Searching for User...");
		long userId = 3;
		Optional<User> userFound = userRepository.findById(userId);
		System.out.println("Found user: " + userFound);


		ChapterRating chapterRating = new ChapterRating(8, "17_20_14_12_2024");

		chapterRating.setUser(userFound.get());
		System.out.println("ChapterRating user: " + chapterRating.getUser());

		chapterRating.setManga(mangaFound.get());
		System.out.println("ChapterRating Manga: " + chapterRating.getManga());

		chapterRating.setChapter(chapterFound.get());
		System.out.println("ChapterRating Chapter: " + chapterRating.getChapter());

		System.out.println("Saving Manga Rating: " + chapterRating);
		chapterRatingRepository.save(chapterRating);
		System.out.println("Successfully saved Chapter Rating!");
	}

	private void deleteSubscription(SubscriptionRepository subscriptionRepository, UserRepository userRepository, MangaRepository mangaRepository) {

		System.out.println("Deleting subscription");

		System.out.println("Searching for user...");
		long userId = 3;
		Optional<User> userFound = userRepository.findById(userId);
		System.out.println("Found user: " + userFound);

		System.out.println("User's subscriptions" + userFound.get().getSubscriptions());

		Subscription subscription = userFound.get().getSubscriptions().getFirst();

		System.out.println("Deleted subscription: " + subscription);
		subscriptionRepository.delete(subscription);

		System.out.println("Successfully deleted subscription!");
	}

	private void createMangaRating(MangaRatingRepository mangaRatingRepository, UserRepository userRepository, MangaRepository mangaRepository) {
		System.out.println("Creating Manga Rating...");

		System.out.println("Searching for User...");
		long userId = 3;
		Optional<User> userFound = userRepository.findById(userId);
		System.out.println("Found user: " + userFound);

		System.out.println("Searching for Manga...");
		long mangaId = 9;
		Optional<Manga> mangaFound = mangaRepository.findById(mangaId);
		System.out.println("Found manga: " + mangaFound);

		MangaRating mangaRating = new MangaRating(8, "12_50_14_12_2024");

		mangaRating.setUser(userFound.get());
		System.out.println("MangaRating user: " + mangaRating.getUser());

		mangaRating.setManga(mangaFound.get());
		System.out.println("MangaRating Manga: " + mangaRating.getManga());

		System.out.println("Saving Manga Rating: " + mangaRating);
		mangaRatingRepository.save(mangaRating);
		System.out.println("Successfully saved Manga Rating!");
	}

	private void createSubscription(SubscriptionRepository subscriptionRepository, UserRepository userRepository, MangaRepository mangaRepository) {
		System.out.println("Creating subscription...");

		System.out.println("Searching for User...");
		long userId = 3;
		Optional<User> userFound = userRepository.findById(userId);
		System.out.println("Found user: " + userFound);

		System.out.println("Searching for Manga...");
		long mangaId = 9;
		Optional<Manga> mangaFound = mangaRepository.findById(mangaId);
		System.out.println("Found manga: " + mangaFound);

		Subscription subscription = new Subscription("12_38_14_12_2024");

		subscription.setManga(mangaFound.get());
		System.out.println("Attached manga: " + subscription.getManga());

		subscription.setUser(userFound.get());
		System.out.println("Attached user: " + subscription.getUser());

		System.out.println("Saving subscription: " + subscription);
		subscriptionRepository.save(subscription);
		System.out.println("Successfully saved subscription!");


	}

	private void createChapter(ChapterRepository chapterRepository) {
		System.out.println("Creating chapter...");

		Chapter chapter = new Chapter("The proccess", 2, "12_30_14_12_2024", "Siupppppppsbi dfbsiua disua bn3452423re3fds fds543543nasoiufnbosiuafnbioasdfas");

		System.out.println("Searching for manga...");
		long mangaId = 11;
		Optional<Manga> mangaFound = mangaRepository.findById(mangaId);

		chapter.setManga(mangaFound.get());
		System.out.println("Chapter's manga:" + chapter.getManga());

		System.out.println("Saving chapter: " + chapter);
		chapterRepository.save(chapter);

		System.out.println("Successfully saved chapter!");




	}

	private void createRole(RoleRepository roleRepository) {
		System.out.println("Creating Role...");

		Role role = new Role("Creator");

		System.out.println("Saving role: " + role);

		roleRepository.save(role);

		System.out.println("Successfully created role!");
	}

	private void createMangaWithJpa(UserRepository userRepository, GenreRepository genreRepository, MangaRepository mangaRepository) {
		long userId = 3;
		System.out.println("Searching for the User with the id: " + userId);
		Optional<User> userFound = userRepository.findById(userId);
		System.out.println("User found: " + userFound);


		Manga manga = new Manga("The Barbarian", "The BarbarianThe BarbarianTheBarbarianTheBarbarianThe BarbarianThe BarbarianThe BarbarianThe Barbarian The BarbarianThe BarbarianThe Barbarian");

		System.out.println("Creating manga: " + manga);

		manga.setAutor(userFound.get());
		System.out.println("Manga autor: " + manga.getAutor());

		long genreId = 1;
		System.out.println("Searching for the Genre with the id: " + genreId);
		Optional<Genre> genreFound = genreRepository.findById(genreId);
		System.out.println("Genre found: " + genreFound);

		manga.add(genreFound.get());
		System.out.println("Manga genre: " + manga.getGenres());

		System.out.println("Saving manga: " + manga);
		mangaRepository.save(manga);

		System.out.println("Successfully created manga!");


	}

	private void createUserWitchJpa(UserRepository userRepository, RoleRepository roleRepository) {
		System.out.println("Creating User...");
		User user = new User("norbas", "123test123", "test123@gmail.com", "17_28_14_12_2024");

		System.out.println("Searching for role 1...");
		long roleId1 = 1;
		Optional<Role> roleFound1 = roleRepository.findById(roleId1);
		System.out.println("Role found: " + roleFound1);

		System.out.println("Searching for role 2...");
		long roleId2 = 2;
		Optional<Role> roleFound2 = roleRepository.findById(roleId2);
		System.out.println("Role found: " + roleFound2);

		user.add(roleFound1.get());
		user.add(roleFound2.get());

		System.out.println("User's roles: " + user.getRoles());

		System.out.println("Saving User: " + user);
		userRepository.save(user);
		System.out.println("Successfully created User");

	}




}
