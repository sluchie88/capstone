/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.service;

import com.tomtom.personalLibrary.data.ObjectNotInDatabaseException;
import com.tomtom.personalLibrary.data.UnitOfWork;
import com.tomtom.personalLibrary.models.AppUser;
import com.tomtom.personalLibrary.models.Book;
import com.tomtom.personalLibrary.models.Recommendation;
import org.springframework.stereotype.Service;

/**
 *
 * @author TomTom
 */
@Service
public class AppUserService {

    UnitOfWork willyLoman;

    public AppUserService(UnitOfWork uow) {
        this.willyLoman = uow;
    }

    public String addBookToProfile(String userName,
            Book book,
            String shelf) {
        if (shelf.equals("hasRead")) {
            willyLoman.addHasReadBook(book, userName);
            return userName;
        }
        if (shelf.equals("currentlyReading")) {
            willyLoman.addCurrentlyReadingBook(book, userName);
            return userName;
        }
        if (shelf.equals("wantToRead")) {
            willyLoman.addWantToReadBook(book, userName);
            return userName;
        }
        return null;
    }

    public AppUser addUser(AppUser user) {
        return willyLoman.addUser(user, user.getPasswordHash());
    }

    public Book addHaveReadBook(String userName, Book currBook) {
        AppUser user = new AppUser();
        try {
            user = willyLoman.getUserInfo(userName);
        } catch (ObjectNotInDatabaseException onide) {
            return null;
        }

        if (user.getCurrentlyReading() == null) {
            willyLoman.addHasReadBook(currBook, userName);
        } else if (user.getWantToRead().contains(currBook)) {
            willyLoman.updateToHaveReadBook(userName, currBook);
        } else if (user.getCurrentlyReading().equals(currBook)) {
            willyLoman.updateToHaveReadBook(userName, currBook);
        } else if (willyLoman.addHasReadBook(currBook, userName)) {

        } else {
            return null;
        }
        return currBook;
    }

    public Book addWantToReadBook(String userName, Book currBook) {
        if (willyLoman.addWantToReadBook(currBook, userName)) {
            return currBook;
        } else {
            return null;
        }
    }

    /**
     * Takes in a new currently reading book. Grabs the old currently reading
     * book for the user, removes the currently reading status and marks it as
     * read. Sets the new book as currently reading at the end. If unsuccessful,
     * returns null
     *
     * @param userName
     * @param currBook
     * @return
     */
    public Book addCurrentlyReadingBook(String userName, Book currBook) {
        AppUser user = new AppUser();
        try {
            user = willyLoman.getUserInfo(userName);
        } catch (ObjectNotInDatabaseException onide) {
            return null;
        }

        Book finished = user.getCurrentlyReading();
        if(finished == null){
            willyLoman.addCurrentlyReadingBook(currBook, userName);
            return currBook;
        }
        
        willyLoman.updateToHaveReadBook(userName, finished);
        if (user.getWantToRead().contains(currBook)) {
            if (willyLoman.updateToHaveReadBook(userName, currBook)) {
                return currBook;
            } else {
                return null;
            }
        } else if (willyLoman.addCurrentlyReadingBook(currBook, userName)) {
            return currBook;
        } else {
            return null;
        }
    }

    public AppUser getUserByUsername(String userName) throws ObjectNotInDatabaseException {
        return willyLoman.getUserInfo(userName);
    }

    public Recommendation addRecommendation(String username, Recommendation rec) {
        return willyLoman.addRecommendation(username, rec);
    }


}
