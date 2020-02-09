/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.models;

/**
 *
 * @author TomTom
 */
public class UserBook {

    private String bookRating;
    private String bookStatus;

    public UserBook() {
    }

    public UserBook(String rating, String hWC) {
        this.bookRating = rating;
        this.bookStatus = hWC;
    }

    public String getBookRating() {
        return bookRating;
    }

    public void setBookRating(String bookRating) {
        this.bookRating = bookRating;
    }    

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus;
    }    
    
}
