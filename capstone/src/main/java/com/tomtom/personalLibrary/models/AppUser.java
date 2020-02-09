/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.models;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author TomTom
 */
public class AppUser {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String passwordHash;
    
    private int roleId;
    private int experiencePoints;
    private int level;
    private Level currentLevel;
    
    private List<Book> wantToRead;
    private List<Book> hasRead;
    private Book currentlyReading;
    
    private List<Challenge> incompleteChallenges;
    private List<Challenge> completeChallenges;
    private List<Recommendation> recommendations;

    public AppUser() {
    }

    public AppUser(String userName, String email, String passwordHash) {
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperiencePoints() {
        return experiencePoints;
    }

    public void setExperiencePoints(int experiencePoints) {
        this.experiencePoints = experiencePoints;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }

    public List<Book> getWantToRead() {
        return wantToRead;
    }

    public void setWantToRead(List<Book> wantToRead) {
        this.wantToRead = wantToRead;
    }

    public List<Book> getHasRead() {
        return hasRead;
    }

    public void setHasRead(List<Book> hasRead) {
        this.hasRead = hasRead;
    }

    public Book getCurrentlyReading() {
        return currentlyReading;
    }

    public void setCurrentlyReading(Book currentlyReading) {
        this.currentlyReading = currentlyReading;
    }

    public List<Challenge> getIncompleteChallenges() {
        return incompleteChallenges;
    }

    public void setIncompleteChallenges(List<Challenge> incompleteChallenges) {
        this.incompleteChallenges = incompleteChallenges;
    }

    public List<Challenge> getCompleteChallenges() {
        return completeChallenges;
    }

    public void setCompleteChallenges(List<Challenge> completeChallenges) {
        this.completeChallenges = completeChallenges;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.userName);
        hash = 89 * hash + Objects.hashCode(this.firstName);
        hash = 89 * hash + Objects.hashCode(this.lastName);
        hash = 89 * hash + Objects.hashCode(this.email);
        hash = 89 * hash + Objects.hashCode(this.passwordHash);
        hash = 89 * hash + this.roleId;
        hash = 89 * hash + this.experiencePoints;
        hash = 89 * hash + Objects.hashCode(this.currentLevel);
        hash = 89 * hash + Objects.hashCode(this.wantToRead);
        hash = 89 * hash + Objects.hashCode(this.hasRead);
        hash = 89 * hash + Objects.hashCode(this.currentlyReading);
        hash = 89 * hash + Objects.hashCode(this.incompleteChallenges);
        hash = 89 * hash + Objects.hashCode(this.completeChallenges);
        hash = 89 * hash + Objects.hashCode(this.recommendations);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AppUser other = (AppUser) obj;
        if (this.roleId != other.roleId) {
            return false;
        }
        if (this.experiencePoints != other.experiencePoints) {
            return false;
        }
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.passwordHash, other.passwordHash)) {
            return false;
        }
        if (!Objects.equals(this.currentLevel, other.currentLevel)) {
            return false;
        }
        if (!Objects.equals(this.wantToRead, other.wantToRead)) {
            return false;
        }
        if (!Objects.equals(this.hasRead, other.hasRead)) {
            return false;
        }
        if (!Objects.equals(this.currentlyReading, other.currentlyReading)) {
            return false;
        }
        if (!Objects.equals(this.incompleteChallenges, other.incompleteChallenges)) {
            return false;
        }
        if (!Objects.equals(this.completeChallenges, other.completeChallenges)) {
            return false;
        }
        if (!Objects.equals(this.recommendations, other.recommendations)) {
            return false;
        }
        return true;
    }

    
    
}
