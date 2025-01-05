package com.db.manga.controller;

import com.db.manga.entity.nosql.*;
import com.db.manga.service.NoSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@Profile({"sql", "objsql", "nosql"})
public class Home {
    private String tempUserId = "67769ae010286664a18318f2";

    @Autowired
    private NoSqlService theService;


    @Profile("nosql")
    @GetMapping("/public/")
    public String homePage(Model model) {
        List<Manga> mangaList = theService.getAllMangas();

        List<Rating> ratingList = theService.findAllMangaRatingByUserId(tempUserId);

        List<Subscription> subscriptionList = theService.findAllSubscriptionsByUserId(tempUserId);

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
    public String viewChapters(@PathVariable String id, Model model) {

        Manga manga = theService.getMangaById(id);

        List<Chapter> chapters = theService.getChaptersByMangaId(id);

        List<Rating> ratingList = theService.findAllChapterRatingByMangaIdAndByUserId(id, tempUserId);

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
    public String rateManga(@PathVariable String id, @RequestParam int rating) {
        System.out.println("Rating for manga " + id + ": " + rating);
        theService.addMangaRating(id, tempUserId,rating, String.valueOf(new Date()));

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
                             @RequestParam String content){

        Manga manga = theService.getMangaById(id);

        if(tempUserId.equals(String.valueOf(manga.getAuthorId()))){
            theService.createChapter(title, episodeNumber, String.valueOf(new Date()), content, String.valueOf(manga.getId()));
        }

        return "redirect:/public/";
    }

    @PostMapping("/reader/manga/{mangaid}/chapter/{chapterid}/rate")
    public String rateChapter(@PathVariable String mangaid, @PathVariable String chapterid, @RequestParam int rating){
        theService.addChapterRating(chapterid, mangaid, tempUserId, rating, String.valueOf(new Date()));
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
                           @RequestParam List<String> genres){

        System.out.println("Start...");
        theService.createManga(title, description, tempUserId, genres);

        return "redirect:/public/";
    }

    @GetMapping("/reader/manga/{id}/subscribe")
    public String subscribeManga(@PathVariable String id){
        theService.subscribeManga(String.valueOf(new Date()), id, tempUserId);

        return "redirect:/public/";
    }

    @GetMapping("/reader/manga/{id}/unsubscribe")
    public String unSubscribeManga(@PathVariable String id){
        theService.unsubscribeManga(id, tempUserId);

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


}


