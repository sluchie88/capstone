/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.service;

/**
 *
 * @author TomTom
 */
public class InvalidISBNException extends Exception {

    public InvalidISBNException(String message) {
        super(message);
    }

    public InvalidISBNException(String message, Throwable cause) {
        super(message, cause);
    }
}
