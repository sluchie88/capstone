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
public class Level {
    private int level;
    private int requiredXP;
    private String imageURL;
    private String rankName;

    public Level() {
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getRequiredXP() {
        return requiredXP;
    }

    public void setRequiredXP(int requiredXP) {
        this.requiredXP = requiredXP;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }
    
    
}
