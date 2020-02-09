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
public class Recommendation {

    private int recommendationId;
    private int recommendationValue;
    private String isbn;

    public Recommendation() {
    }

    public Recommendation(String isbn) {
        this.isbn = isbn;
    }
    
    public int getRecommendationId() {
        return recommendationId;
    }

    public void setRecommendationId(int recommendationId) {
        this.recommendationId = recommendationId;
    }

    public int getRecommendationValue() {
        return recommendationValue;
    }

    public void setRecommendationValue(int recommendationValue) {
        this.recommendationValue = recommendationValue;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
