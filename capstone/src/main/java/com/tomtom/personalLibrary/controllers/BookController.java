/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.controllers;

import com.tomtom.personalLibrary.models.Book;
import com.tomtom.personalLibrary.service.BookService;
import com.tomtom.personalLibrary.service.ChallengeService;
import com.tomtom.personalLibrary.security.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author TomTom
 */
@Controller
public class BookController {

    private final BookService bookService;
    private final UserService userService;
    private final ChallengeService challengeService;

    public BookController(BookService bServ, UserService uServ, ChallengeService cServ) {
        this.bookService = bServ;
        this.userService = uServ;
        this.challengeService = cServ;
    }
    
    @PostMapping("bookInfo/")
    public ResponseEntity<String> displayBook(@RequestBody Book book) {
        HttpStatus status = HttpStatus.OK;
        try{
            bookService.addBookToDB(book);
        }catch(Exception ex){
            status = HttpStatus.UNPROCESSABLE_ENTITY;//422
        }
        return new ResponseEntity(book, status);
    }
    
    @GetMapping("bookInfo/{isbn}")
    public String displayBookInfo(@PathVariable String isbn, Model model){
        Book book = bookService.getBookByIsbn(isbn);
        HttpStatus status = HttpStatus.OK;
        
        if(book == null){
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        }
        model.addAttribute(book);
        return "bookInfo.html";
    }
}
