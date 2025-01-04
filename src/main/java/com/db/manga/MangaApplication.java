package com.db.manga;

import com.db.manga.service.NoSqlService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Profile("nosql")
@SpringBootApplication
public class MangaApplication {

	private NoSqlService noSqlService;

	public static void main(String[] args) {
		SpringApplication.run(MangaApplication.class, args);
	}

	public MangaApplication(NoSqlService noSqlService) {
		this.noSqlService = noSqlService;
	}

	@Bean
	public CommandLineRunner commandLineRunner(NoSqlService noSqlService) {

		return runner -> {
			//getUser(noSqlService);
			//createUser(noSqlService);
			//getAllUsers(noSqlService);
			//updateUser(noSqlService);
			//createManga(noSqlService);
			//creatingChapter(noSqlService);
			//subscribe(noSqlService);
			//getChaptersByMangaId(noSqlService);
			//getMangaRatingsByUserId(noSqlService);
			//createGenre(noSqlService);

		};

	}

	private void createGenre(NoSqlService noSqlService) {
		noSqlService.createGenre("Action");
		noSqlService.createGenre("Romance");
		noSqlService.createGenre("Sci-Fi");
		noSqlService.createGenre("Adventure");
	}

	private void getMangaRatingsByUserId(NoSqlService noSqlService) {
		noSqlService.findAllMangaRatingByUserId("67769ae010286664a18318f2");
	}

	private void getChaptersByMangaId(NoSqlService noSqlService) {
		noSqlService.getChaptersByMangaId("6776abb7888109612d8e419f");
	}

	private void subscribe(NoSqlService noSqlService) {
		noSqlService.subscribeManga("19_05_02_01_2025", "6776abb7888109612d8e419f", "67769c07109a892a73c946ce");
	}

	private void creatingChapter(NoSqlService noSqlService) {
		noSqlService.createChapter("The proccess", 2, "19_05_02_01_2025",
				"fipodshjfipodsh f hdipso    !!!!  fpidshj fds f pds9oujf opds'jufsdpofujd s'p9 fpe'ah 'oipdfhsaipof yh089ewy432432 dsad  ufg  of8d gs ui ofdgsi ufgdsuf d",
				"6776abb7888109612d8e419f");

	}

	private void createManga(NoSqlService noSqlService) {
		List<String> list= new ArrayList<>();
		list.add("Action");
		list.add("Romance");

		noSqlService.createManga("Doom Breaker", "Zephyr is the last human fighting evil in a world abandoned by the gods. When he is killed in battle by Tartarus, the god of destruction, all hope for humanity seems lost.", "67769ae010286664a18318f2", list);
	}

	private void updateUser(NoSqlService noSqlService) {
		noSqlService.updateUser("67769ae010286664a18318f2", "ogarix", "passwordzik1", "ogarix@mail.com", "");
	}

	private void getAllUsers(NoSqlService noSqlService) {
		noSqlService.getAllUsers();
	}

	private void createUser(NoSqlService noSqlService) {
		List<String> list= new ArrayList<>();
			list.add("Reader");
		noSqlService.createUser("norbas", "haslo123", "norbas@mail.com", "14_59_02_01_2025", list);
	}

	private void getUser(NoSqlService noSqlService) {
		noSqlService.getUserById("6730d6e6f24e553ce804cc9c");
	}

}
