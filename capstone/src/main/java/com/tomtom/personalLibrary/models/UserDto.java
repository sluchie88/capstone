/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.models;

/**
 * Used for registering and authenticating user information on
 * login
 * @author TomTom
 */
public class UserDto {
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private int roleId;
    
    public UserDto() {
    }

    public UserDto(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public UserDto(String userName, String email, String password, int roleId) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
