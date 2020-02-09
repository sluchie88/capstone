/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomtom.personalLibrary.data;

import com.tomtom.personalLibrary.TestApplicationConfiguration;
import com.tomtom.personalLibrary.models.AppUser;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 *
 * @author TomTom
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class UserDAOTest {
    
    @Autowired
    AppUserDAO dao;
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of findUser method, of class AppUserDAO.
     */
    @Test
    public void testFindUser() {
        AppUser fromDao = dao.findUserByUsername("weedLazerz");
        assertTrue(fromDao.getFirstName().equals("Chon"));
        assertThrows(EmptyResultDataAccessException.class,
                () -> dao.findUserByUsername("HellsElbow"));
    }
//
//    /**
//     * Test of changeUserPassword method, of class AppUserDAO.
//     */
//    @Test
//    public void testChangeUserPassword() {
//    }

//    /**
//     * Test of checkPasswordHash method, of class AppUserDAO.
//     */
//    @Test
//    public void testCheckPasswordHash() {
//    }

    
    /**
     * Test of addUser method, of class AppUserDAO.
     */
    @Test
    public void testAddEditDeleteUser() {
        AppUser user = new AppUser();
        String userName = "xXMosesXx";
        user.setUserName(userName);
        user.setEmail("asdf@gmail.com");
        user.setFirstName("Jason");
        user.setLastName("Aldi");
        user.setExperiencePoints(0);
        user.setRoleId(2);
        String passwordHash = "gobbledyGook";
        
        AppUser inDao = dao.addUser(user, passwordHash);
        assertEquals(user, inDao);
        
        user.setUserName("MrBojangles");
        assertTrue(dao.editUser(user, userName));
        
        dao.deleteUser(user, passwordHash);
        assertThrows(EmptyResultDataAccessException.class,
                () -> dao.findUserByUsername(user.getUserName()));
    }

    
    
}
