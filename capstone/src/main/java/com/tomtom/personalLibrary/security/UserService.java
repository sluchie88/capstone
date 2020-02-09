/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.security;

import com.tomtom.personalLibrary.data.ObjectNotInDatabaseException;
import com.tomtom.personalLibrary.data.UnitOfWork;
import com.tomtom.personalLibrary.models.AppUser;
import com.tomtom.personalLibrary.models.Book;
import com.tomtom.personalLibrary.models.UserDto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author TomTom
 */
@Service
public class UserService implements UserDetailsService {

    UnitOfWork bigBlack;

    public UserService(UnitOfWork uow) {
        this.bigBlack = uow;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDto userDto = bigBlack.findByUsername(userName);
        if (userDto == null) {
            throw new UsernameNotFoundException(userName);
        }

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        if (userDto.getRoleId() == 1) {
            roles.add(new SimpleGrantedAuthority("ADMIN"));
            roles.add(new SimpleGrantedAuthority("USER"));
        } else {
            roles.add(new SimpleGrantedAuthority("USER"));
        }
        User user = new User(userDto.getUserName(),
                userDto.getPassword(),
                roles);
        return user;
    }

}
/*
@Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        AppUser appUser = dao.findByUserName(userName);
        if (appUser == null) {
            throw new UsernameNotFoundException(userName);
        }

        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        for (String role : appUser.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role));
        }

        AppUser user = new AppUser(appUser.getUserName(), appUser.getPasswordHash(), roles);

        return user;
    }
 */
