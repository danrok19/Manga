package com.db.manga.controller;


import com.db.manga.config.security.sql.CustomUserDetails;
import com.db.manga.entity.sql.*;
import com.db.manga.service.ObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Profile("obj")
public class ObjController {


    @Autowired
    private ObjService theService;

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal CustomUserDetails auth) {

        String username = auth.getUsername();

        model.addAttribute("username", username);

        System.out.println("Id usera: " + auth.getId());

        List<Manga> mangaList = theService.getAllMangas();

        List<MangaRating> ratingList = theService.findAllMangaRatingByUserId(auth.getId());

        Long longId = Long.parseLong(auth.getId());
        List<Subscription> subscriptionList = theService.findAllSubscriptionsByUserId(longId);

        // creating a map of {mangaId = ratingValue}
        Map<String, Integer> ratingsMap = ratingList.stream()
                .collect(Collectors.toMap(rating -> String.valueOf(rating.getManga().getId()), MangaRating::getRating));

        Map<String, String> subscriptionsMap = subscriptionList.stream()
                .collect(Collectors.toMap(subscription -> String.valueOf(subscription.getManga().getId()),
                        subscription -> String.valueOf(subscription.getId())));



        model.addAttribute("sql", true);
        model.addAttribute("mangaList", mangaList);
        model.addAttribute("ratingsMap", ratingsMap);
        model.addAttribute("subscriptionsMap", subscriptionsMap);
        model.addAttribute("mangaCount", mangaList.size());

        return "home";
    }

    @GetMapping("/reader/profile")
    public String getProfile(Model model, @AuthenticationPrincipal CustomUserDetails auth){
        model.addAttribute("username", auth.getUsername());
        model.addAttribute("email", auth.getEmail());

        List<Role> roles = auth.getRoles();

        List<String> roleDescriptions = roles.stream()
                .map(Role::getRoleDescription)  // Wyciąganie tylko roleDescription
                .collect(Collectors.toList());

        model.addAttribute("roles", roleDescriptions);
        model.addAttribute("signupDate", auth.getSignupDate());

        Long longId = Long.parseLong(auth.getId());
        List<Subscription> subs = theService.findAllSubscriptionsByUserId(longId);

        List<Manga> mangas = new ArrayList<>();
        for(Subscription sub : subs){
            mangas.add(theService.getMangaById(sub.getManga().getId()));
        }
        model.addAttribute("mangas", mangas);

        List<Manga> mangaList = theService.getMangaByUserId(longId);
        model.addAttribute("myMangas", mangaList);

        return "user-profile";
    }

    @GetMapping("/public/manga/{id}/chapters")
    public String viewChapters(@PathVariable String id, Model model, @AuthenticationPrincipal CustomUserDetails auth) {

        String username = auth.getUsername();

        model.addAttribute("username", username);

        Manga manga = theService.getMangaById(Long.parseLong(id));

        List<Chapter> chapters = theService.getChaptersByMangaId(Long.parseLong(id));

        List<ChapterRating> ratingList = theService.findAllChapterRatingByMangaIdAndByUserId(Long.parseLong(id), Long.parseLong(auth.getId()));

        // creating a map of {chapterid = ratingValue}
        Map<String, Integer> ratingsMap = ratingList.stream()
                .collect(Collectors.toMap(rating -> String.valueOf(rating.getChapter().getId()), ChapterRating::getRating));

        model.addAttribute("manga", manga);
        model.addAttribute("ratingsMap", ratingsMap);
        model.addAttribute("chapters", chapters);
        model.addAttribute("userId", auth.getId());
        model.addAttribute("authorId", String.valueOf(manga.getAutor().getId()));

        return "chapters";
    }


    @GetMapping("/user/manga/{id}/chapters/form")
    public String getFormChapter(@PathVariable String id, Model model, @AuthenticationPrincipal CustomUserDetails auth){
        String username = auth.getUsername();

        model.addAttribute("username", username);
        Manga manga = theService.getMangaById(Long.parseLong(id));
        model.addAttribute("manga", manga);

        return "chapter-form";
    }

    @PostMapping("/user/manga/{id}/chapters/save")
    public String addChapter(@PathVariable String id,
                             @RequestParam String title,
                             @RequestParam int episodeNumber,
                             @RequestParam String content,
                             @AuthenticationPrincipal CustomUserDetails auth,
                             Model model){
        String username = auth.getUsername();

        model.addAttribute("username", username);

        Manga manga = theService.getMangaById(Long.parseLong(id));

        if(auth.getId().equals(String.valueOf(manga.getAutor().getId()))){
            theService.createChapter(title, episodeNumber, String.valueOf(new Date()), content, manga.getId());
        }

        return "redirect:/";
    }

    @GetMapping("/user/manga/form")
    public String getFormManga(Model model, @AuthenticationPrincipal CustomUserDetails auth){
        String username = auth.getUsername();

        model.addAttribute("username", username);
        List<Genre> genres = theService.findAllGenres();

        model.addAttribute("genres", genres);
        return "manga-form";
    }

    @PostMapping("/user/manga/form/save")
    public String addManga(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam List<String> genres,
                           @AuthenticationPrincipal CustomUserDetails auth,
                           Model model){
        String username = auth.getUsername();

        List<Genre> genreList = new ArrayList<>();
        for(String genre : genres){
            genreList.add(theService.findGenreByName(genre));
        }

        model.addAttribute("username", username);
        System.out.println("Start...");
        theService.createManga(title, description, Long.parseLong(auth.getId()), genreList);

        return "redirect:/";
    }

    @GetMapping("/public/chapter/{id}")
    public String viewChapter(@PathVariable String id, Model model, @AuthenticationPrincipal CustomUserDetails auth) {
        String username = auth.getUsername();

        model.addAttribute("username", username);
        Chapter chapter = theService.findChapterById(Long.parseLong(id));

        if (chapter == null) {
            throw new RuntimeException("Chapter not found with ID: " + id);
        }

        model.addAttribute("chapter", chapter);
        return "chapter"; // Szablon "chapter.html"
    }


    @PostMapping("/reader/manga/{id}/rate")
    public String rateManga(@PathVariable String id, @RequestParam int rating, @AuthenticationPrincipal CustomUserDetails auth) {
        System.out.println("Rating for manga " + id + ": " + rating);
        theService.addMangaRating(Long.parseLong(id), Long.parseLong(auth.getId()), rating, String.valueOf(new Date()));

        return "redirect:/"; // Przekierowanie na stronę główną
    }

    @PostMapping("/reader/manga/{mangaid}/chapter/{chapterid}/rate")
    public String rateChapter(@PathVariable String mangaid, @PathVariable String chapterid, @RequestParam int rating, @AuthenticationPrincipal CustomUserDetails auth, Model model){
        String username = auth.getUsername();

        model.addAttribute("username", username);
        theService.addChapterRating(Long.parseLong(chapterid), Long.parseLong(mangaid), Long.parseLong(auth.getId()), rating, String.valueOf(new Date()));
        return "redirect:/";
    }


    @GetMapping("/reader/manga/{id}/subscribe")
    public String subscribeManga(@PathVariable String id, @AuthenticationPrincipal CustomUserDetails auth){
        theService.subscribeManga(String.valueOf(new Date()), Long.parseLong(id), Long.parseLong(auth.getId()));

        return "redirect:/";
    }

    @GetMapping("/reader/manga/{id}/unsubscribe")
    public String unSubscribeManga(@PathVariable String id, @AuthenticationPrincipal CustomUserDetails auth){
        theService.unsubscribeManga(Long.parseLong(id));

        return "redirect:/";
    }

    @GetMapping("/user/manga/{mangaid}/chapter/{chapterid}/delete")
    public String deleteChapter(@PathVariable String mangaid, @PathVariable String chapterid){

        theService.deleteChapter(Long.parseLong(chapterid));

        return "redirect:/";
    }

    @GetMapping("/user/manga/{id}/delete")
    public String deleteManga(@PathVariable String id){

        theService.deleteManga(Long.parseLong(id));

        return "redirect:/";
    }


    @GetMapping("/register")
    public String registerPage(Model model){
        List<Role> roles = theService.findAllRoles();

        List<String> roleDescriptions = roles.stream()
                .map(Role::getRoleDescription)  // Wyciąganie tylko roleDescription
                .collect(Collectors.toList());

        model.addAttribute("roles", roleDescriptions);
        return "register";
    }

    @PostMapping("/public/register/")
    public String registerUser(@RequestParam String username,
                               @RequestParam String password,
                               @RequestParam String email,
                               @RequestParam List<String> roles){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        List<Role> roleList = new ArrayList<>();
        for(String role : roles){
            Role tempRole = theService.findRoleByRoleDescription(role);
            roleList.add(tempRole);
        }

        theService.createUser(username, passwordEncoder.encode(password), email, String.valueOf(new Date()), roleList);

        return "/";
    }
}
