package com.db.manga.controller;

import com.db.manga.config.security.nosql.CustomUserDetails;
import com.db.manga.entity.nosql.*;
import com.db.manga.service.NoSqlService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
@Profile({"sql", "objsql", "nosql"})
public class Home {
    //private String tempUserId = "67769ae010286664a18318f2";

    @Autowired
    private NoSqlService theService;


    @Profile("nosql")
    @GetMapping("/public/")
    public String homePage(Model model, @AuthenticationPrincipal CustomUserDetails auth) {

        String username = auth.getUsername();  // Uzyskiwanie nazwy użytkownika

        model.addAttribute("username", username);

        System.out.println("Id usera: " + auth.getId());

        List<Manga> mangaList = theService.getAllMangas();

        List<Rating> ratingList = theService.findAllMangaRatingByUserId(auth.getId());

        List<Subscription> subscriptionList = theService.findAllSubscriptionsByUserId(auth.getId());

        // creating a map of {mangaId = ratingValue}
        Map<String, Integer> ratingsMap = ratingList.stream()
                .collect(Collectors.toMap(rating -> String.valueOf(rating.getMangaId()), Rating::getRatingValue));

        Map<String, String> subscriptionsMap = subscriptionList.stream()
                        .collect(Collectors.toMap(subscription -> String.valueOf(subscription.getMangaId()),
                                subscription -> String.valueOf(subscription.getId())));



        model.addAttribute("mangaList", mangaList);
        model.addAttribute("ratingsMap", ratingsMap);
        model.addAttribute("subscriptionsMap", subscriptionsMap);
        model.addAttribute("mangaCount", mangaList.size());

        return "home";
    }


    @Profile("nosql")
    @GetMapping("/public/manga/{id}/chapters")
    public String viewChapters(@PathVariable String id, Model model, @AuthenticationPrincipal CustomUserDetails auth) {


        Manga manga = theService.getMangaById(id);

        List<Chapter> chapters = theService.getChaptersByMangaId(id);

        List<Rating> ratingList = theService.findAllChapterRatingByMangaIdAndByUserId(id, auth.getId());

        // creating a map of {chapterid = ratingValue}
        Map<String, Integer> ratingsMap = ratingList.stream()
                .collect(Collectors.toMap(rating -> String.valueOf(rating.getChapterId()), Rating::getRatingValue));

        model.addAttribute("manga", manga);
        model.addAttribute("ratingsMap", ratingsMap);
        model.addAttribute("chapters", chapters);

        return "chapters";
    }

    @GetMapping("/public/chapter/{id}")
    public String viewChapter(@PathVariable String id, Model model) {
        Chapter chapter = theService.findChapterById(id);

        if (chapter == null) {
            throw new RuntimeException("Chapter not found with ID: " + id);
        }

        model.addAttribute("chapter", chapter);
        return "chapter"; // Szablon "chapter.html"
    }

    @PostMapping("/reader/manga/{id}/rate")
    public String rateManga(@PathVariable String id, @RequestParam int rating, @AuthenticationPrincipal CustomUserDetails auth) {
        System.out.println("Rating for manga " + id + ": " + rating);
        theService.addMangaRating(id, auth.getId(), rating, String.valueOf(new Date()));

        return "redirect:/public/"; // Przekierowanie na stronę główną
    }


    @GetMapping("/user/manga/{id}/chapters/form")
    public String getFormChapter(@PathVariable String id, Model model){
        Manga manga = theService.getMangaById(id);
        model.addAttribute("manga", manga);

        return "chapter-form";
    }

    @PostMapping("/user/manga/{id}/chapters/save")
    public String addChapter(@PathVariable String id,
                             @RequestParam String title,
                             @RequestParam int episodeNumber,
                             @RequestParam String content,
                             @AuthenticationPrincipal CustomUserDetails auth){

        Manga manga = theService.getMangaById(id);

        if(auth.getId().equals(String.valueOf(manga.getAuthorId()))){
            theService.createChapter(title, episodeNumber, String.valueOf(new Date()), content, String.valueOf(manga.getId()));
        }

        return "redirect:/public/";
    }

    @PostMapping("/reader/manga/{mangaid}/chapter/{chapterid}/rate")
    public String rateChapter(@PathVariable String mangaid, @PathVariable String chapterid, @RequestParam int rating, @AuthenticationPrincipal CustomUserDetails auth){
        theService.addChapterRating(chapterid, mangaid, auth.getId(), rating, String.valueOf(new Date()));
        return "redirect:/public/";
    }

    @GetMapping("/user/manga/form")
    public String getFormManga(Model model){
        List<Genre> genres = theService.findAllGenres();

        model.addAttribute("genres", genres);
        return "manga-form";
    }

    @PostMapping("/user/manga/form/save")
    public String addManga(@RequestParam String title,
                           @RequestParam String description,
                           @RequestParam List<String> genres,
                           @AuthenticationPrincipal CustomUserDetails auth){

        System.out.println("Start...");
        theService.createManga(title, description, auth.getId(), genres);

        return "redirect:/public/";
    }

    @GetMapping("/reader/manga/{id}/subscribe")
    public String subscribeManga(@PathVariable String id, @AuthenticationPrincipal CustomUserDetails auth){
        theService.subscribeManga(String.valueOf(new Date()), id, auth.getId());

        return "redirect:/public/";
    }

    @GetMapping("/reader/manga/{id}/unsubscribe")
    public String unSubscribeManga(@PathVariable String id, @AuthenticationPrincipal CustomUserDetails auth){
        theService.unsubscribeManga(id, auth.getId());

        return "redirect:/public/";
    }

    @GetMapping("/user/manga/{mangaid}/chapter/{chapterid}/delete")
    public String deleteChapter(@PathVariable String mangaid, @PathVariable String chapterid){

        theService.deleteChapter(chapterid);

        return "redirect:/public/";
    }

    @GetMapping("/user/manga/{id}/delete")
    public String deleteManga(@PathVariable String id){

        theService.deleteManga(id);

        return "redirect:/public/";
    }

    @GetMapping("/user/profile")
    public String getProfile(Model model, @AuthenticationPrincipal CustomUserDetails auth){
        model.addAttribute("username", auth.getUsername());
        model.addAttribute("email", auth.getEmail());
        model.addAttribute("roles", auth.getRoles());
        model.addAttribute("signupDate", auth.getSignupDate());

        List<Subscription> subs = theService.findAllSubscriptionsByUserId(auth.getId());

        List<Manga> mangas = new ArrayList<>();
        for(Subscription sub : subs){
            mangas.add(theService.getMangaById(String.valueOf(sub.getMangaId())));
        }
        model.addAttribute("mangas", mangas);

        model.addAttribute("myMangas", theService.getMangaByUserId(auth.getId()));

        return "user-profile";
    }


}


