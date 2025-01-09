package com.db.manga.controller;


import com.db.manga.config.security.objsql.CustomUserDetails;
import com.db.manga.entity.sql.*;
import com.db.manga.service.ObjSqlService;
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
@Profile("objsql")
public class ObjSqlController {

    @Autowired
    private ObjSqlService theService;

    @GetMapping("/login")
    String login() {
        return "login";
    }

    @GetMapping("/")
    public String homePage(Model model, @AuthenticationPrincipal CustomUserDetails auth){

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
