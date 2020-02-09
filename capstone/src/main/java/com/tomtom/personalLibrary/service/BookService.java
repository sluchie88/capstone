/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.service;

import com.tomtom.personalLibrary.data.UnitOfWork;
import com.tomtom.personalLibrary.models.Book;
import com.tomtom.personalLibrary.models.Genre;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author TomTom
 */
@Service
public class BookService {
    
    private final UnitOfWork riri;
    
    public BookService(UnitOfWork uow){
        this.riri = uow;
    }
    
    public Book addBookToDB(Book book){
        Book novel = riri.getBookByISBN(book.getIsbn());
        if(novel == null){
            List<Genre> cleaned = cleanGenres(book.getGenre());
            book.setGenre(cleaned);
            riri.addBook(book);
            return book;
        }
        return null;
    }
    
    public Book getBookByIsbn(String isbn){
        return riri.getBookByISBN(isbn);
    }

    private List<Genre> cleanGenres(List<Genre> genre) {
        List<Genre> ret = new ArrayList<>();
        for(Genre g : genre){
            String[] tokens = g.getGenreName().split(" / ");
            for(String s : tokens){
                Genre jean = new Genre(s);
                if(!ret.contains(jean)){
                    ret.add(jean);
                }
            }
        }
        return ret;
    }
}
