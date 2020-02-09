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
public class Challenge {
    private int challengeId;
    private String challengeName;
    private String challengeDescription;
    private int experienceValue;
    private String badgeURL;
    private boolean completed;

    public Challenge() {
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeDescription() {
        return challengeDescription;
    }

    public void setChallengeDescription(String challengeDescription) {
        this.challengeDescription = challengeDescription;
    }

    public int getExperienceValue() {
        return experienceValue;
    }

    public void setExperienceValue(int experienceValue) {
        this.experienceValue = experienceValue;
    }

    public String getBadgeURL() {
        return badgeURL;
    }

    public void setBadgeURL(String badgeURL) {
        this.badgeURL = badgeURL;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(int challengeId) {
        this.challengeId = challengeId;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
