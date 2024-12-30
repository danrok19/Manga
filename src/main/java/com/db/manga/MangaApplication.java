package com.db.manga;

import com.db.manga.dao.sql.GenreRepository;
import com.db.manga.entity.sql.Genre;
import com.db.manga.entity.sql.Manga;
import com.db.manga.entity.sql.Role;
import com.db.manga.entity.sql.User;

import com.db.manga.service.ObjSqlService;
import com.db.manga.service.ObjSqlServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class MangaApplication {
	private final GenreRepository genreRepository;
	private ObjSqlService objSqlService;

	public MangaApplication(ObjSqlService objSqlService, GenreRepository genreRepository) {
		this.objSqlService = objSqlService;
		this.genreRepository = genreRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(MangaApplication.class, args);
	}


	@Bean
	public CommandLineRunner commandLineRunner(ObjSqlService objSqlService) {

		return runner -> {
			//getUser(objSqlService);

			//createUser(objSqlService);

			//updateUser(objSqlService);

			//deleteUser(objSqlService);

			//createGenre(objSqlService);

			//findGenres(objSqlService);

			//createManga(objSqlService);

			//findManga(objSqlService);

			//createChapter(objSqlService);

			//createRole(objSqlService);

			//subscribe(objSqlService);

			//rateManga(objSqlService);

			//changeRating(objSqlService);

			//rateChapter(objSqlService);

			//changeChapterRating(objSqlService);
		};

}

	private void changeChapterRating(ObjSqlService objSqlService) {

		objSqlService.changeChapterRating(1, 8, "20_23_29_12_2024");
	}

	private void rateChapter(ObjSqlService objSqlService) {
		objSqlService.addChapterRating(1, 1, 1, 10, "20_00_29_12_2024");
	}

	private void changeRating(ObjSqlService objSqlService) {
		objSqlService.changeMangaRating(1, 7, "22_06_28_12_2024");
	}

	private void rateManga(ObjSqlService objSqlService) {
		objSqlService.addMangaRating(1, 1, 9, "21_30_28_12_2024");
	}

	private void subscribe(ObjSqlService objSqlService) {
		objSqlService.subscribeManga("14_40_28_12_2024", 1, 1);
	}

	private void createRole(ObjSqlService objSqlService) {
		objSqlService.createRole("Reader");
		objSqlService.createRole("Creator");

	}

	private void createChapter(ObjSqlService objSqlService) {
		objSqlService.createChapter("The beggining", 1, "13_45_28_12_2024",
				"fipodshjfipodsh f hdipsoh fiodsh fdpos hjfpidshj fds f pds9oujf opds'jufsdpofujd s'p9 fpe'ah 'oipdfhsaipof yh089ewyh f8egf sdio f98ds ghfuidlo ksfyt hsoui gfidus gfdis ufg  of8d gs ui ofdgsi ufgdsuf d",
				1L);
	}

	private void findManga(ObjSqlService objSqlService) {
		Manga manga = objSqlService.getMangaById(1L);
		System.out.println("Found manga: " + manga);
	}

	private void findGenres(ObjSqlService objSqlService) {

		objSqlService.findAllGenres();
	}

	private void createManga(ObjSqlService objSqlService) {
		List<Genre> genres = objSqlService.findAllGenres();

		objSqlService.createManga("Solo Leveling Ragnarok", "Solo Leveling: Ragnarok is a Korean web novel written by Daul. It is a spinoff and the sequel series of Solo Leveling.",
				1, genres);
	}

	private void createGenre(ObjSqlService objSqlService) {
		objSqlService.createGenre("Action");

		objSqlService.createGenre("Romance");

		objSqlService.createGenre("Science-Fiction");
	}

	private void deleteUser(ObjSqlService objSqlService) {
		int userId = 10;

		objSqlService.deleteUser(userId);
	}

	private void updateUser(ObjSqlService objSqlService) {

		objSqlService.updateUser(9, "ogarix", "passwordzik123", "ogarix@gmail.com", "14_00_15_12__2024");
	}

	private void createUser(ObjSqlService objSqlService) {
		List<Role> roles = objSqlService.findAllRoles();
		objSqlService.createUser("norbas", "haslo123", "norbas@gmail.com", "15_10_28_12_2024", roles);
	}

	private void getUser(ObjSqlService objSqlService) {
		User user = objSqlService.getUserById(10);
		System.out.println("User: " + user);
	}


}
