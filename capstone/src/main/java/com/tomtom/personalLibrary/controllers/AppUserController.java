/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.controllers;

import com.tomtom.personalLibrary.data.ObjectNotInDatabaseException;
import com.tomtom.personalLibrary.models.AppUser;
import com.tomtom.personalLibrary.models.Book;
import com.tomtom.personalLibrary.models.Recommendation;
import com.tomtom.personalLibrary.models.UserBook;
import com.tomtom.personalLibrary.service.BookService;
import com.tomtom.personalLibrary.service.AppUserService;
import java.security.Principal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author TomTom
 */
@Controller
public class AppUserController {

    //private static final String KEY = "AIzaSyCFyXxr63Vw0UETFpTRl1J9dW74ldYsfvo";
    private final AppUserService appUserServ;
    private final BookService bookServ;

    public AppUserController(AppUserService uServ, BookService bServ) {
        this.appUserServ = uServ;
        this.bookServ = bServ;
    }

    @GetMapping("/userInfo")
    public String getUserInfo(Principal prince, Model model) {
        String username = prince.getName();
        return "redirect:/userInfo/" + username;
    }

    @GetMapping("/userInfo/{username}")
    public String getUserInfo(@PathVariable String username, Model model) {
        AppUser appUser = new AppUser();
        try {
            appUser = appUserServ.getUserByUsername(username);
        } catch (ObjectNotInDatabaseException ex) {
        }
        model.addAttribute(appUser);
        return "userInfo.html";
    }

    @PostMapping("/bookInfo/{isbn}")
    public String addUserBook(@PathVariable String isbn, UserBook thoughts, Principal bellding) {
        String username = bellding.getName();
        HttpStatus status = HttpStatus.OK;
        Book currBook = bookServ.getBookByIsbn(isbn);
        Book added = new Book();

        if (thoughts.getBookStatus() != null) {
            if (thoughts.getBookStatus().equalsIgnoreCase("have")) {
                added = appUserServ.addHaveReadBook(username, currBook);
                if (added == null) {
                    status = HttpStatus.UNPROCESSABLE_ENTITY;
                }
            }
            if (thoughts.getBookStatus().equalsIgnoreCase("currently")) {
                added = appUserServ.addCurrentlyReadingBook(username, currBook);
                if (added == null) {
                    status = HttpStatus.UNPROCESSABLE_ENTITY;
                }
            }
            if (thoughts.getBookStatus().equalsIgnoreCase("want")) {
                added = appUserServ.addWantToReadBook(username, currBook);
                if (added == null) {
                    status = HttpStatus.UNPROCESSABLE_ENTITY;
                }
            }
        }
        if (thoughts.getBookRating() != null) {
            Recommendation rec = new Recommendation(isbn);
            
            if (thoughts.getBookRating().equalsIgnoreCase("buy")) {
                rec.setRecommendationValue(1);
                Recommendation retRec = appUserServ.addRecommendation(username, rec);
                if (retRec == null) {
                    status = HttpStatus.UNPROCESSABLE_ENTITY;
                }

            }
            if (thoughts.getBookRating().equalsIgnoreCase("borrow")) {
                rec.setRecommendationValue(2);
                Recommendation retRec = appUserServ.addRecommendation(username, rec);
                if (retRec == null) {
                    status = HttpStatus.UNPROCESSABLE_ENTITY;
                }
            }
            if (thoughts.getBookRating().equalsIgnoreCase("skip")) {
                rec.setRecommendationValue(1);
                Recommendation retRec = appUserServ.addRecommendation(username, rec);
                if (retRec == null) {
                    status = HttpStatus.UNPROCESSABLE_ENTITY;
                }
            }
        }
        return "redirect:/userInfo/" + username;
    }
}
