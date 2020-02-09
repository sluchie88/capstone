/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.controllers;

import com.tomtom.personalLibrary.data.UserDtoDAO;
import com.tomtom.personalLibrary.models.AppUser;
import com.tomtom.personalLibrary.models.UserDto;
import com.tomtom.personalLibrary.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author TomTom
 */
@RestController
public class AuthenticationController {
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    UserDtoDAO utdDao;
    
    @Autowired
    AppUserService appUserService;
    
    @GetMapping("/admin")
    public String admin(){
        return "<h1>Welcome admin!</h1>";
    }
    
    @PostMapping("/register")
    public void showRegistrationForm(@RequestBody UserDto userDto){
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        
        AppUser user = new AppUser();
        user.setUserName(userDto.getUserName());
        user.setPasswordHash(encodedPassword);
        user.setEmail(userDto.getEmail());
        user.setRoleId(2);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setExperiencePoints(0);
        
        appUserService.addUser(user);   
    }
    
}
