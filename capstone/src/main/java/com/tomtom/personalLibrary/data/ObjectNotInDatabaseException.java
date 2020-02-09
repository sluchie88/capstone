/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

/**
 *
 * @author TomTom
 */
public class ObjectNotInDatabaseException extends Exception{
    
    public ObjectNotInDatabaseException(String message) {
        super(message);
    }

    public ObjectNotInDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
